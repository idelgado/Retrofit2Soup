package org.idelgado.retrofit2soup.activity.rx;

import android.os.Bundle;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ModelObjectAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.model.Repo;
import org.idelgado.retrofit2soup.provider.GithubService;
import org.idelgado.retrofit2soup.util.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxTopReposActivity extends BaseActivity {

    List<Repo> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GithubService.GitHubRx github = GithubService.createRx();

        final Observable<List<Contributor>> observable = github.contributors("square", "retrofit");

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<Contributor>, Observable<List<Contributor>>>() {
                    @Override
                    public Observable<List<Contributor>> call(List<Contributor> contributors) {

                        Collection<Contributor> frequentContributors = Filter.isFrequentContributor(contributors);
                        List<Contributor> frequentContributorsList = new ArrayList<>(frequentContributors);

                        return Observable.just(frequentContributorsList);
                    }
                })
                .flatMap(new Func1<List<Contributor>, Observable<List<Repo>>>() {
                    @Override
                    public Observable<List<Repo>> call(List<Contributor> contributors) {
                        Observable<List<Repo>> observers = null;

                        for(Contributor contributor : contributors) {

                            Observable<List<Repo>> reposObserver = github.repos(contributor.login)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread());

                            if(observers == null) {
                                observers = reposObserver;
                            } else {
                                // Merge it with previous observable
                                observers = observers.concatWith(reposObserver);
                            }
                        }

                        return observers;
                    }
                })
                .subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Completed");
                        ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(results);
                        recyclerView.setAdapter(modelObjectAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        results.addAll(Filter.containsAndroid(repos));
                    }
                });

    }

}

package org.idelgado.retrofit2soup.activity.rx;

import android.os.Bundle;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ModelObjectAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.provider.GithubService;
import org.idelgado.retrofit2soup.util.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubService.GitHubRx github = GithubService.createRx();

        Observable<List<Contributor>> observable = github.contributors("square", "retrofit");

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
                .subscribe(new Subscriber<List<Contributor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(contributors);
                        recyclerView.setAdapter(modelObjectAdapter);
                    }
                });

    }

}

package org.idelgado.retrofit2soup.activity.rx;

import android.os.Bundle;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ModelObjectAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.provider.GithubService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubService.GitHubRx github = GithubService.createRx();

        Observable<List<Contributor>> observable = github.contributors("square", "retrofit");

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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

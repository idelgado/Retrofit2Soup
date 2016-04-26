package org.idelgado.retrofit2soup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.idelgado.retrofit2soup.R;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.provider.GithubService;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(this.getClass().getSimpleName());

        GithubService.GitHubRx github = GithubService.createRx();

        Observable<List<Contributor>> observable = github.contributors("square", "retrofit");

        observable.subscribeOn(Schedulers.newThread())
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
                for (Contributor contributor : contributors) {
                    Timber.i(contributor.login + " (" + contributor.contributions + ")");
                }
            }
        });

    }

}

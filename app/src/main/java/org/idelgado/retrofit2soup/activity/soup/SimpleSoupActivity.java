package org.idelgado.retrofit2soup.activity.soup;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ContributorAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.provider.GithubService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleSoupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubService.GitHub github = GithubService.create();

        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();

                ContributorAdapter contributorAdapter = new ContributorAdapter(contributors);
                recyclerView.setAdapter(contributorAdapter);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                // What can we do here?
            }
        });

    }

}

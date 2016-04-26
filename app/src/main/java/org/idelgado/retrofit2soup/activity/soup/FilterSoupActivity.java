package org.idelgado.retrofit2soup.activity.soup;

import android.os.Bundle;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ModelObjectAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.provider.GithubService;
import org.idelgado.retrofit2soup.util.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterSoupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubService.GitHub github = GithubService.create();

        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();

                Collection<Contributor> frequentContributors = Filter.isFrequentContributor(contributors);

                ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(new ArrayList(frequentContributors));
                recyclerView.setAdapter(modelObjectAdapter);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });

    }

}

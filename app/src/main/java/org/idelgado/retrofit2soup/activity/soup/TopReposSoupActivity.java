package org.idelgado.retrofit2soup.activity.soup;

import android.os.Bundle;

import org.idelgado.retrofit2soup.activity.base.BaseActivity;
import org.idelgado.retrofit2soup.adapter.ModelObjectAdapter;
import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.model.Repo;
import org.idelgado.retrofit2soup.provider.GithubService;
import org.idelgado.retrofit2soup.util.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopReposSoupActivity extends BaseActivity {

    Set<Repo> repoSet = new HashSet<>();
    int callbackCount = 0;
    int contributorSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GithubService.GitHub github = GithubService.create();

        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();

                Collection<Contributor> frequentContributors = Filter.isFrequentContributor(contributors);

                contributorSize = frequentContributors.size();

                for(Contributor contributor : frequentContributors) {
                    Call<List<Repo>> reposCall = github.repos(contributor.login);
                    reposCall.enqueue(reposCallback());
                }

            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });

    }

    private Callback<List<Repo>> reposCallback() {

        return new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();

                Collection<Repo> containsAndroidCollection = Filter.containsAndroid(repos);

                repoSet.addAll(containsAndroidCollection);

                callbackCount++;

                if(callbackCount  == contributorSize) {
                    setAdapter(repoSet);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        };
    }

    private void setAdapter(Collection frequentContributors) {
        ModelObjectAdapter modelObjectAdapter = new ModelObjectAdapter(new ArrayList(frequentContributors));
        recyclerView.setAdapter(modelObjectAdapter);
    }
}

package org.idelgado.retrofit2soup.provider;

import org.idelgado.retrofit2soup.model.Contributor;
import org.idelgado.retrofit2soup.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public class GithubService {

    public static final String API_URL = "https://api.github.com";

    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
        @GET("/users/{owner}/repos")
        Call<List<Repo>> repos(
                @Path("owner") String owner);
    }

    public static GitHub create() {
        // Create a very simple REST adapter which points the GitHub API.
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.GitHub.class);
    }

    public interface GitHubRx {
        @GET("/repos/{owner}/{repo}/contributors")
        Observable<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
        @GET("/users/{owner}/repos")
        Observable<List<Repo>> repos(
                @Path("owner") String owner);
    }

    public static GitHubRx createRx() {
         // Create a very simple REST adapter which points the GitHub API and provides RXJava calls.
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.GitHubRx.class);
    }

}

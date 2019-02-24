package com.genise.zytec.a04_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> getRepos(@Path("user") String user);

    @GET("users/{user}/repos")
    Observable<List<Repo>> getReposRx(@Path("user") String user);
}

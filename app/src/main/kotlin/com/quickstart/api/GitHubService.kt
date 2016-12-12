package com.quickstart.api

import com.quickstart.models.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created at 29.11.16 11:58
 * @author Alexey_Ivanov
 */
interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Observable<List<Repo>>
}

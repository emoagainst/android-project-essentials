package com.quickstart.api.repos

import android.util.Log
import com.quickstart.api.GitHubService
import com.quickstart.models.Repo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created at 09.12.16 18:07
 * @author Alexey_Ivanov
 */


class ReposAPIService (val githubAPI : GitHubService) {

    var isRequestingRepos = false

    fun getRepos(forUser : String): Flowable<List<Repo>> {
        return githubAPI.listRepos(forUser)
                .doOnSubscribe { isRequestingRepos = true }
                .doOnTerminate { isRequestingRepos = false }
                .doOnError { th -> handleReposError(th) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun handleReposError(th : Throwable) {
        th.printStackTrace()
        Log.e("[ReposAPIService]", "")
        throw th
    }
}

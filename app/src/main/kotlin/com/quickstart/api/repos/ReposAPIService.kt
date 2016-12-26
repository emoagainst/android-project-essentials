package com.quickstart.api.repos

import com.quickstart.api.GitHubService
import com.quickstart.api.cacheRemoteList
import com.quickstart.api.remoteFlowable
import com.quickstart.models.Repo
import io.reactivex.Flowable

/**
 * Created at 09.12.16 18:07
 * @author Alexey_Ivanov
 */


class ReposAPIService(val githubAPI: GitHubService) {

    var isRequestingRepos = false

    fun getRepos(forUser: String): Flowable<List<Repo>> {
        return githubAPI.listRepos(forUser).remoteFlowable()
                .doOnSubscribe { isRequestingRepos = true }
                .doOnTerminate { isRequestingRepos = false }
                .doOnNext { repos ->
                    cacheRemoteList(repos)
                }
                .doOnError { th -> handleReposError(th) }
    }

    private fun handleReposError(th: Throwable) {
        th.printStackTrace()
        throw th
    }
}

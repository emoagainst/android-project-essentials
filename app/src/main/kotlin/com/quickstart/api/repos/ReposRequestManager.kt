package com.quickstart.api.repos

import android.util.Log
import com.quickstart.models.Repo
import io.reactivex.Flowable

/**
 * Created at 12.12.16 13:10
 * @author Alexey_Ivanov
 */


class ReposRequestManager(val reposAPIService: ReposAPIService, val reposLocalService: ReposLocalService) {
    private val TAG = "[ReposRequestMan]"
    fun isRequestingInformation() = reposAPIService.isRequestingRepos

    fun getRepos(): Flowable<List<Repo>> {
        val remoteRepos = reposAPIService.getRepos("emoagainst")
        val localRepos = reposLocalService.getRepos()

        val zippedStreams = Flowable.merge(localRepos, remoteRepos).doOnError { th-> th.printStackTrace()}.doOnNext{ Log.d(TAG, "on Next merge = $it")}
        return zippedStreams
    }
}

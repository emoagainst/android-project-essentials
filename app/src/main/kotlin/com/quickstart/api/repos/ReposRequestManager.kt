package com.quickstart.api.repos

import android.util.Log
import com.quickstart.models.Repo
import com.quickstart.utils.asFlowable
import com.quickstart.utils.getRealm
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created at 12.12.16 13:10
 * @author Alexey_Ivanov
 */


class ReposRequestManager(val reposAPIService: ReposAPIService) {
    private val TAG = "[ReposRequestMan]"
    fun isRequestingInformation() = reposAPIService.isRequestingRepos

    fun getRepos(): Flowable<List<Repo>> {
        val remoteRepos = reposAPIService.getRepos("emoagainst")
        val localRepos = getRealm().where(Repo::class.java).findAll()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError { th -> th.printStackTrace() }
                .doOnNext { repos -> repos.forEach { it.name = "${it.name} cached"} }

        val zippedStreams = Flowable.merge(localRepos, remoteRepos).doOnError { th-> th.printStackTrace()}.doOnNext{ Log.d(TAG, "on Next merge = $it")}
        return zippedStreams
    }
}

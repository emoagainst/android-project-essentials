package com.quickstart.api.repos

import com.quickstart.models.Repo
import com.quickstart.utils.asFlowable
import com.quickstart.utils.getRealm
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*

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
                .subscribeOn(Schedulers.computation())
                .doOnError { th -> th.printStackTrace() }
                .doOnNext { repos -> repos.forEach { it.name = "${it.name} cached"} }

        val zippedStreams =  Flowable.zip(localRepos, remoteRepos, BiFunction<List<Repo>, List<Repo>, List<Repo>> { t1, t2 ->
            val list = ArrayList<Repo>()
            list.addAll(t2)
            list.addAll(t1)
            list
        }).doOnError { th-> th.printStackTrace() }
        return zippedStreams
    }
}

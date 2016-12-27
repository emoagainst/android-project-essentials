package com.quickstart.api.repos

import android.util.Log
import com.quickstart.models.Repo
import com.quickstart.utils.asFlowable
import com.quickstart.utils.getRealm
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created at 27.12.16 12:42
 * @author Alexey_Ivanov
 */
class ReposLocalService (){
    fun getRepos() : Flowable<List<Repo>> {
        Log.d("[ReposLocalService]", "getRepos with $this");
        return getRealm().where(Repo::class.java).findAll()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError { th -> th.printStackTrace() }
                .doOnNext { repos -> repos.forEach { it.name = "${it.name} cached"} }
    }
}

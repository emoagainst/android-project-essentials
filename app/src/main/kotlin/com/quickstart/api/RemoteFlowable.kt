package com.quickstart.api

import com.quickstart.utils.getRealm
import com.quickstart.utils.performTransactionAndClose
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.RealmObject

/**
 * Created at 26.12.16 13:44
 * @author Alexey_Ivanov
 */

fun <T> Observable<T>.toRemoteFlowable():Flowable<T> =
            this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.BUFFER)

fun <T: RealmObject> Observable<List<T>>.toRemoteCacheableFlowable():Flowable<List<T>> =
        toRemoteFlowable()
                .doOnNext(::cacheRemoteList)

fun cacheRemoteList (list: List<RealmObject>) {
    getRealm().performTransactionAndClose { realm ->
        realm.copyToRealmOrUpdate(list)
    }
}

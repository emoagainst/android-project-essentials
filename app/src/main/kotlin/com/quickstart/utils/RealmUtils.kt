package com.quickstart.utils

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

/**
 * Created at 15.12.16 14:44
 * @author Alexey_Ivanov
 */
fun getRealm(): Realm {
    return Realm.getDefaultInstance()
}

fun <Return> useRealm(work: (Realm) -> Return) : Return  = getRealm().use(work)

inline fun <Return> Realm.performTransactionAndClose(perform: (Realm) -> Return): Return = use { realm ->
    try {
        beginTransaction()
        val result = perform(realm)
        commitTransaction()
        result
    }
    catch(err: Throwable) {
        if (isInTransaction) cancelTransaction()
        throw err
    }
}

inline fun <Return> Realm.executeTransactionAndReturn (crossinline perform: (Realm) -> Return) : Return? = let { realm ->
    var result: Return? = null
    realm.executeTransaction {
        result = perform(realm)
    }
    result
}

inline fun <E: RealmModel> RealmResults<E>.asFlowable() : Flowable<List<E>> {
    return useRealm {realm ->
        Flowable.fromArray(realm.copyFromRealm(this))
    }
}

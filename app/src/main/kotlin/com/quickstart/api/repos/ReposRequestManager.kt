package com.quickstart.api.repos

import com.quickstart.models.Repo
import io.reactivex.Flowable

/**
 * Created at 12.12.16 13:10
 * @author Alexey_Ivanov
 */


class ReposRequestManager (val reposAPIService: ReposAPIService){

    fun isRequestingInformation() = reposAPIService.isRequestingRepos

    fun getRepos () : Flowable<List<Repo>> {
        return reposAPIService.getRepos("emoagainst")
    }
}

package com.quickstart.mvp.repos

import android.util.Log
import com.quickstart.api.GitHubService
import com.quickstart.models.Repo
import com.quickstart.mvp.BaseView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created at 29.11.16 10:58
 * @author Alexey_Ivanov
 */

class ReposPresenter @Inject constructor(val reposView : ReposContract.View, val api : GitHubService) : ReposContract.Presenter{

    @Inject
    fun setupListeners(){
        Log.d("[ReposPresenter]", "setupListeners")
        reposView.setPresenter(this)
    }

    override fun onViewResumed() {
    }

    override fun onViewAttached(viewCallback: BaseView<*>) {
    }

    override fun onViewDetached() {
    }

    override fun loadRepos() {
        api.listRepos("emoagainst").enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                Log.d("ReposPresenter", response?.body().toString());
            }
        });
    }

}

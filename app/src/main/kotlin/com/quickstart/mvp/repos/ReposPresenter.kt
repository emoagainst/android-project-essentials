package com.quickstart.mvp.repos

import com.quickstart.mvp.BaseView
import javax.inject.Inject

/**
 * Created at 29.11.16 10:58
 * @author Alexey_Ivanov
 */

class ReposPresenter @Inject constructor(val reposView : ReposContract.View) : ReposContract.Presenter{

    @Inject
    fun setListeners(){
        reposView.presenter = this
    }

    override fun onViewResumed() {
    }

    override fun onViewAttached(viewCallback: BaseView<*>) {
    }

    override fun onViewDetached() {
    }

    override fun loadRepos() {
//        api.listRepos("emoagainst").enqueue(object : Callback<List<Repo>> {
//            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
//                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
//                Log.d("ReposPresenter", response?.body().toString());
//            }
//        });
    }

}

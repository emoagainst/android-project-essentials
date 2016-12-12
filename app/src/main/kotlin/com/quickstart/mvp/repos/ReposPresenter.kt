package com.quickstart.mvp.repos

import android.util.Log
import com.quickstart.api.repos.ReposRequestManager
import com.quickstart.models.Repo
import com.quickstart.mvp.BaseView
import io.reactivex.disposables.Disposable
import io.reactivex.processors.AsyncProcessor
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Created at 29.11.16 10:58
 * @author Alexey_Ivanov
 */

class ReposPresenter @Inject constructor(val reposView: ReposContract.View, val reposRequestManager: ReposRequestManager) : ReposContract.Presenter {

    @Inject
    fun setupListeners() {
        Log.d("[ReposPresenter]", "setupListeners")
        reposView.setPresenter(this)
    }

    lateinit var reposProcessor: AsyncProcessor <List<Repo>>
    var reposDisposable: Disposable? = null

    override fun onViewResumed() {
        reposDisposable?.let {
            reposProcessor.subscribe(ReposSubcriber())
        }
    }

    override fun onViewAttached(viewCallback: BaseView<*>) {

    }

    override fun onViewDetached() {
    }

    override fun loadRepos() {

        reposProcessor = AsyncProcessor.create<List<Repo>>()
        reposDisposable = reposProcessor.subscribeWith(ReposSubcriber())

        reposRequestManager.getRepos().subscribe(reposProcessor)
    }

    fun isNetworkingRequestMade() = reposDisposable !=null

    inner class ReposSubcriber : DisposableSubscriber<List<Repo>>() {
        override fun onNext(repos: List<Repo>) {
            if (repos.isEmpty()) {
                reposView.showEmptyRepos()
            } else {
                reposView.showRepos(repos)
            }
        }

        override fun onComplete() {
            reposView.setLoadingIndicator(false)
        }

        override fun onError(t: Throwable?) {
            reposView.setLoadingIndicator(false)
        }
    }
}

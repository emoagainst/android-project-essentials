package com.quickstart.mvp.repos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quickstart.models.Repo

/**
 * Created at 29.11.16 10:34
 * @author Alexey_Ivanov
 */
class ReposFragment() : Fragment(), ReposContract.View{

    lateinit var mPresenter: ReposContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance():ReposFragment{
            return ReposFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mPresenter.loadRepos()
        return view
    }

    override fun setPresenter(t: ReposContract.Presenter) {
        mPresenter = checkNotNull(t)
    }

    override fun showRepos(repos: List<Repo>) {

    }

    override fun showEmptyRepos() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingIndicator(active: Boolean) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

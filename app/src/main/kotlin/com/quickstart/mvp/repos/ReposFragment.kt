package com.quickstart.mvp.repos

import android.support.v4.app.Fragment
import com.quickstart.models.Repo

/**
 * Created at 29.11.16 10:34
 * @author Alexey_Ivanov
 */
class ReposFragment() : Fragment(), ReposContract.View{

    lateinit override var presenter: ReposContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance():ReposFragment{
            return ReposFragment()
        }
    }



    override fun showRepos(repos: List<Repo>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyRepos() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingIndicator(active: Boolean) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

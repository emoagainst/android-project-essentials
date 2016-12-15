package com.quickstart.mvp

import android.support.v4.app.Fragment

/**
 * Created at 28.11.16 16:24
 * @author Alexey_Ivanov
 */



interface BaseView<Presenter: BasePresenter>{
    var mPresenter : Presenter
}




interface BasePresenter {

    fun onViewResumed()
    fun onViewAttached(viewCallback : BaseView<*>)
    fun onViewDetached()
}

abstract class BaseFragment<Presenter: BasePresenter> : Fragment(), BaseView<Presenter>{
    override fun onStart() {
        super.onStart()
        mPresenter.onViewAttached(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onViewDetached()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onViewResumed()
    }
}


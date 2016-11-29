package com.quickstart.mvp

/**
 * Created at 28.11.16 16:24
 * @author Alexey_Ivanov
 */
interface BaseView<T>{
    var presenter : T
}

interface BasePresenter {

    fun onViewResumed()
    fun onViewAttached(viewCallback : BaseView<*>)
    fun onViewDetached()
}

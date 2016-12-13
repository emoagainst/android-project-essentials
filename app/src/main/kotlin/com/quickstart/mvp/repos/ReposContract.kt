package com.quickstart.mvp.repos

import com.quickstart.activities.MainActivity
import com.quickstart.dagger.modules.RequestManagerComponent
import com.quickstart.models.Repo
import com.quickstart.mvp.BasePresenter
import com.quickstart.mvp.BaseView
import com.quickstart.utils.FragmentScoped
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created at 28.11.16 16:27
 * @author Alexey_Ivanov
 */

interface ReposContract {

    interface View : BaseView<Presenter> {
        fun showRepos(repos: List<Repo>)
        fun showEmptyRepos()
        fun setLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadRepos()
    }
}

@Module
class ReposPresenterModule(val view: ReposContract.View) {
    @Provides
    @FragmentScoped
    fun provideReposContractView(): ReposContract.View = view
}

@FragmentScoped
@Component(dependencies = arrayOf(RequestManagerComponent::class), modules = arrayOf(ReposPresenterModule::class))
interface ReposPresenterComponent {
    fun inject(activity: MainActivity)
}


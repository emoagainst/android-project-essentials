package com.quickstart

import android.app.Application
import com.quickstart.api.repos.ReposRequestManager
import com.quickstart.dagger.modules.ApiModule
import com.quickstart.dagger.modules.ApplicationModule
import com.quickstart.dagger.modules.RequestManagerModules
import dagger.Component
import javax.inject.Singleton

/**
 * Created at 24.11.16 11:59
 * @author Alexey_Ivanov
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class, RequestManagerModules::class))
interface ApplicationComponent {
    fun inject(application: Application)
    fun getReposManager() : ReposRequestManager
}

class Application : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule())
                .requestManagerModules(RequestManagerModules())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}

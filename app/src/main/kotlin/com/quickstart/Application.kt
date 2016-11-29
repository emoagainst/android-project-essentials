package com.quickstart

import android.app.Application
import com.quickstart.api.GitHubService
import com.quickstart.dagger.modules.ApiModule
import com.quickstart.dagger.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created at 24.11.16 11:59
 * @author Alexey_Ivanov
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {
    fun inject(application: Application)
    fun getApi () : GitHubService
}

class Application : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}

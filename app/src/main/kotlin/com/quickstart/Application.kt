package com.quickstart

import android.app.Application
import com.quickstart.api.GitHubService
import com.quickstart.dagger.modules.*
import dagger.Component
import javax.inject.Singleton

/**
 * Created at 24.11.16 11:59
 * @author Alexey_Ivanov
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: Application)
}

class Application : Application() {
    private val apiModule by lazy { ApiModule() }
    private val applicationModule by lazy { ApplicationModule(this) }

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(applicationModule)
                .build()
    }

    val requestManagerComponent: RequestManagerComponent by lazy {
        DaggerRequestManagerComponent
                .builder()
                .applicationModule(applicationModule)
                .apiModule(apiModule)
                .requestManagerModules(RequestManagerModules())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}

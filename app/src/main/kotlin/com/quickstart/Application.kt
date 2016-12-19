package com.quickstart

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.support.multidex.MultiDexApplication
import com.google.gson.Gson
import com.quickstart.api.GitHubService
import com.quickstart.dagger.modules.*
import com.quickstart.dagger.scopes.RestApiScope
import dagger.Component
import io.realm.Realm
import javax.inject.Singleton

/**
 * Created at 24.11.16 11:59
 * @author Alexey_Ivanov
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: Application)
    fun sharedPreferences(): SharedPreferences
    fun context() : Context
    fun gson():Gson
}

@RestApiScope
@Component (dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ApiModule::class))
interface ApiComponent {
    fun gitHubService() : GitHubService
}

class Application : MultiDexApplication() {

    private val apiModule by lazy { ApiModule() }
    private val applicationModule by lazy { ApplicationModule(this) }

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(applicationModule)
                .build()
    }

    val apiComponent : ApiComponent by lazy {
        DaggerApiComponent
                .builder()
                .applicationComponent(applicationComponent)
                .apiModule(apiModule)
                .build()
    }

    val requestManagerComponent: RequestManagerComponent by lazy {
        DaggerRequestManagerComponent
                .builder()
                .apiComponent(apiComponent)
                .requestManagerModules(RequestManagerModules())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)

        Realm.init(this)
    }
}

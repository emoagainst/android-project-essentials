package com.quickstart

import android.app.Application
import com.quickstart.activities.MainActivity
import com.quickstart.api.CookieJar
import com.quickstart.dagger.modules.AndroidModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created at 24.11.16 11:59
 * @author Alexey_Ivanov
 */

@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {
    fun inject(application: Application)
    fun inject(activity: MainActivity)
    fun inject(jar: CookieJar)
}

class Application : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .androidModule(AndroidModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}

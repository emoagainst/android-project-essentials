package com.quickstart.dagger.modules

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quickstart.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created at 24.11.16 12:46
 * @author Alexey_Ivanov
 */

@Module
class AndroidModule (val application: Application) {

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences =
         PreferenceManager.getDefaultSharedPreferences(application)

    @Provides @Singleton
    fun provideGSON() : Gson =
         GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Provides
    fun provideContext() = application.applicationContext
}



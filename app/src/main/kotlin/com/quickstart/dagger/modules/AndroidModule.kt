package com.quickstart.dagger.modules

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quickstart.Application
import com.quickstart.BuildConfig
import com.quickstart.api.GitHubService
import com.quickstart.api.SampleOkHttpClientConfigurator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created at 24.11.16 12:46
 * @author Alexey_Ivanov
 */

@Module
class AndroidModule (val application: Application) {
    @Provides @Singleton
    fun provideApi(): GitHubService{
        val retrofit = Retrofit.Builder()
                .client(SampleOkHttpClientConfigurator(application).client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.HOST)
                .build()

        return retrofit.create(GitHubService::class.java)
    }

    @Provides @Singleton
    fun provideSharedPreferences(): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
    @Provides @Singleton
    fun provideGSON() : Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }
}

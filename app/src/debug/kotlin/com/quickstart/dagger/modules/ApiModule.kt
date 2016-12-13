package com.quickstart.dagger.modules

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.quickstart.BuildConfig
import com.quickstart.api.CookieJar
import com.quickstart.api.GitHubService
import com.quickstart.dagger.scopes.RestApiScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created at 25.11.16 13:50
 * @author Alexey_Ivanov
 */
@Module
class ApiModule() {
    companion object {
        @JvmField
        val CONNECTION_TIMEOUT = 40L //seconds
    }

    @Provides @RestApiScope
    fun provideGitHubService(client: OkHttpClient): GitHubService {
        val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.HOST)
                .build()

        return retrofit.create(GitHubService::class.java)
    }

    @Provides @RestApiScope
    fun provideOkHttpClient(loggingInterceptor: Interceptor, cookieJar: CookieJar) =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .addInterceptor(loggingInterceptor)
                    .build()

    @Provides @RestApiScope
    fun providesCookieJar(context: Context) =
            CookieJar(context)

    @Provides @RestApiScope
    fun provideLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}

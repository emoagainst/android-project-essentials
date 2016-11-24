package com.quickstart.api;


import android.content.Context;

import com.quickstart.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created at 07.04.16 17:54
 *
 * @author Alexey_Ivanov
 */
public class SampleOkHttpClientConfigurator {

    private static final long CONNECTION_TIMEOUT = 40;

    private OkHttpClient client;

    public SampleOkHttpClientConfigurator(Context context) {

        this.client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar())
                .addInterceptor(new LoggingInterceptorFactory(BuildConfig.DEBUG).makeLoggingInterceptor())
                .build();
    }

    public OkHttpClient getClient() {

        return client;
    }

    private static class LoggingInterceptorFactory{

        private boolean debuggable;
        private LoggingInterceptorFactory(boolean debuggable) {
            this.debuggable = debuggable;
        }

        HttpLoggingInterceptor makeLoggingInterceptor(){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (debuggable){
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            else {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }
            return loggingInterceptor;
        }
    }
}

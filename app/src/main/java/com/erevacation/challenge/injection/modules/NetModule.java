/*
 *
 *  * Copyright 2017 Kojadin
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.erevacation.challenge.injection.modules;

import android.content.Context;

import com.erevacation.challenge.BuildConfig;
import com.erevacation.challenge.data.remote.GitApi;
import com.erevacation.challenge.data.remote.HttpLoggingInterceptor;
import com.erevacation.challenge.injection.qualifier.AppContext;
import com.erevacation.challenge.injection.scopes.PerApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final int CONNECT_TIMEOUT_SEC = 4;
    private static final int READ_TIMEOUT_SEC = 10;

    @Provides
    @PerApplication
    static Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @PerApplication
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerApplication
    static GitApi provideGitApi(@AppContext Context context, Gson gson, OkHttpClient okHttpClient) {

        OkHttpClient.Builder httpClientBuilder = okHttpClient.newBuilder();

        httpClientBuilder.readTimeout(READ_TIMEOUT_SEC, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        httpClientBuilder.addInterceptor(loggingInterceptor);

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(GitApi.class);
    }
}

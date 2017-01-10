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

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.erevacation.challenge.BuildConfig;
import com.erevacation.challenge.injection.qualifier.AppContext;
import com.erevacation.challenge.injection.scopes.PerApplication;
import com.erevacation.challenge.rxbus.RxEventBus;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class AppModule {

    private static final long SCHEMA_VERSION_REALM = 1;

    private static final String MAIN_REALM = "main_realm.realm";

    private final Application mApp;

    public AppModule(Application app) {
        mApp = app;
    }

    @Provides
    @PerApplication
    public static RealmConfiguration provideMainRealmConfiguration(@AppContext Context context) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context)
                .name(MAIN_REALM)
                .schemaVersion(SCHEMA_VERSION_REALM);

        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded();
        }

        return builder.build();
    }

    @Provides
    public static Realm provideMainRealm(RealmConfiguration realmConfiguration) {
        return Realm.getInstance(realmConfiguration);
    }

    @Provides
    @PerApplication
    public static RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    @PerApplication
    @AppContext
    public Context provideAppContext() {
        return mApp;
    }

    @Provides
    @PerApplication
    public Resources provideResources() {
        return mApp.getResources();
    }
}

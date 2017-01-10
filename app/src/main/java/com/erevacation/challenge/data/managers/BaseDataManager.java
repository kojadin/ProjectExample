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

package com.erevacation.challenge.data.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.CallSuper;

import com.erevacation.challenge.data.remote.GitApi;
import com.erevacation.challenge.injection.qualifier.AppContext;

public abstract class BaseDataManager<R> implements IBaseDataManager {

    protected GitApi gitApi;

    protected Context context;

    private R mRepository;

    public R getRepository() {
        return mRepository;
    }

    public BaseDataManager(@AppContext Context context, GitApi gitApi, R repository) {
        this.context = context;
        this.gitApi = gitApi;
        mRepository = repository;
    }

    @Override
    @CallSuper
    public boolean isOnline() {
        NetworkInfo netInfo =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                        .getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}

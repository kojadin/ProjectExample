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

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulerTransformer {

    private final static ObservableTransformer networkSchedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    private final static ObservableTransformer dbSchedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());

    public static <T> ObservableTransformer<T, T> applyNetworkSchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) networkSchedulersTransformer;
    }

    public static <T> ObservableTransformer<T, T> applyDBSchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) dbSchedulersTransformer;
    }
}

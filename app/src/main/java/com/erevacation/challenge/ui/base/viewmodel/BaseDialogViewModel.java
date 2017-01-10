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

package com.erevacation.challenge.ui.base.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;

import com.erevacation.challenge.injection.qualifier.AppContext;
import com.erevacation.challenge.rxbus.RxEventBus;
import com.erevacation.challenge.ui.base.view.MvvmView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseDialogViewModel<V extends MvvmView> extends BaseObservable implements
        MvvmViewModel<V> {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @AppContext
    @Inject
    protected Context appContext;

    @Inject
    protected ContextThemeWrapper contextThemeWrapper;

    @Inject
    protected Resources resources;

    @Inject
    protected RxEventBus rxEventBus;

    private V mView;

    public V getView() {
        return mView;
    }

    @Override
    @CallSuper
    public void attachView(@NonNull V view, @Nullable Bundle savedInstanceState) {
        mView = view;

        restoreInstanceState(savedInstanceState);

        addObservables();
    }

    @Override
    @CallSuper
    public void detachView() {
        mView = null;
        compositeDisposable.clear();
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
    }

    @Override
    @CallSuper
    public void restoreInstanceState(@Nullable Bundle savedInstanceState) {
    }

    @Override
    @CallSuper
    public void addObservables() {
    }

    protected abstract String getTag();
}

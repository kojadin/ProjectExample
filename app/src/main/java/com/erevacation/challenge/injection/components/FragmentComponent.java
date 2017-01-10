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

package com.erevacation.challenge.injection.components;


import com.erevacation.challenge.injection.modules.FragmentModule;
import com.erevacation.challenge.injection.modules.ViewModelModule;
import com.erevacation.challenge.injection.scopes.PerFragment;
import com.erevacation.challenge.ui.screens.main.productlist.ProductListFragment;
import com.erevacation.challenge.ui.screens.main.productdetails.ProductFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class,
        modules = { FragmentModule.class, ViewModelModule.class })
public interface FragmentComponent {

    void inject(ProductListFragment fragment);

    void inject(ProductFragment fragment);
}
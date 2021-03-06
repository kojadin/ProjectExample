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

import com.erevacation.challenge.ui.screens.main.MainMvvm;
import com.erevacation.challenge.ui.screens.main.MainViewModel;
import com.erevacation.challenge.ui.screens.main.productdetails.IProductMvvm;
import com.erevacation.challenge.ui.screens.main.productlist.IProductListMvvm;
import com.erevacation.challenge.ui.screens.main.productlist.ProductListViewModel;
import com.erevacation.challenge.ui.screens.main.productlist.recyclerview.ProductMvvm;
import com.erevacation.challenge.ui.screens.main.productlist.recyclerview.ProductViewModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {

    // Activities
    @Binds
    abstract MainMvvm.ViewModel bindMainViewModel(MainViewModel viewModel);

    // Fragments
    @Binds
    abstract IProductListMvvm.ViewModel bindSelectYearViewModel(ProductListViewModel viewModel);

    @Binds
    abstract IProductMvvm.ViewModel bindSummaryViewModel(
            com.erevacation.challenge.ui.screens.main.productdetails.ProductViewModel viewModel);

    // View Holders
    @Binds
    abstract ProductMvvm.ViewModel bindYearViewModel(ProductViewModel viewModel);
}

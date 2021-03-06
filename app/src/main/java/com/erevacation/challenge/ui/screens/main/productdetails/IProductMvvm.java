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

package com.erevacation.challenge.ui.screens.main.productdetails;

import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.ui.base.view.MvvmView;
import com.erevacation.challenge.ui.base.viewmodel.MvvmViewModel;

/**
 * Created by kojadin on 12/15/16.
 */

public interface IProductMvvm {

    interface View extends MvvmView {
        android.view.View getRootView();
    }

    interface ViewModel extends MvvmViewModel<View> {
        void update(Product product);

        String getCurrentPrice();

        String getOriginalPrice();

        String getImage();

        String getName();

        String getBrand();
    }
}

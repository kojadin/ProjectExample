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

package com.erevacation.challenge.ui.screens.main.productlist.recyclerview;

import android.view.View;

import com.erevacation.challenge.databinding.RvItemProductBinding;
import com.erevacation.challenge.ui.base.BaseViewHolder;
import com.erevacation.challenge.ui.base.view.MvvmView;


public class ProductViewHolder extends
        BaseViewHolder<RvItemProductBinding, ProductMvvm.ViewModel> implements
        MvvmView {

    public ProductViewHolder(View v) {
        super(v);

        viewHolderComponent().inject(this);
        bindContentView(v);
    }

    public RvItemProductBinding getBinding() {
        return binding;
    }
}

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

import com.erevacation.challenge.injection.modules.ViewHolderModule;
import com.erevacation.challenge.injection.modules.ViewModelModule;
import com.erevacation.challenge.injection.scopes.PerViewHolder;
import com.erevacation.challenge.ui.screens.main.productlist.recyclerview.ProductViewHolder;

import dagger.Component;

@PerViewHolder
@Component(dependencies = AppComponent.class,
        modules = { ViewHolderModule.class, ViewModelModule.class })
public interface ViewHolderComponent {
    // create inject methods for your ViewHolders here
    void inject(ProductViewHolder viewHolder);
}

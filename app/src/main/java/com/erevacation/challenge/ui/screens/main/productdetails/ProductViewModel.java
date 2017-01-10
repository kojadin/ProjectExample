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


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.erevacation.challenge.anotations.RestoringKeysConstant;
import com.erevacation.challenge.anotations.SettingsConstants;
import com.erevacation.challenge.data.managers.product.ProductDataManager;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.ui.base.viewmodel.BaseViewModel;
import com.erevacation.challenge.ui.screens.main.productlist.ProductListViewModel;

import org.parceler.Parcels;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by kojadin on 12/15/16.
 */

public class ProductViewModel extends BaseViewModel<IProductMvvm.View> implements
        IProductMvvm.ViewModel {

    @RestoringKeysConstant
    public static String USER_BUNDLE_KEY;

    static {
        SettingsConstants.populateConstants(ProductListViewModel.class);
    }

    private ProductDataManager mProductDataManager;
    private Product mProduct;

    @Inject
    public ProductViewModel(ProductDataManager productDataManager) {
        this.mProductDataManager = productDataManager;
    }

    @Override
    protected String getTag() {
        return ProductViewModel.class.getSimpleName();
    }

    @Override
    public void attachView(@NonNull IProductMvvm.View view, @Nullable Bundle savedInstanceState) {
        super.attachView(view, savedInstanceState);
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        super.saveInstanceState(outState);
        outState.putParcelable(USER_BUNDLE_KEY, Parcels.wrap(mProduct));
    }

    @Override
    public void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.restoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mProduct = Parcels.unwrap(savedInstanceState.getParcelable(USER_BUNDLE_KEY));
        }
    }

    @Override
    public void update(Product product) {
        mProduct = product;
    }

    @Override
    public String getName() {
        try {
            if(mProduct == null){
                return "";
            }
            return mProduct.getName();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }

    @Override
    public String getImage() {
        try {
            if(mProduct == null){
                return "";
            }
            return mProduct.getImage();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }

    @Override
    public String getCurrentPrice() {
        try {
            if(mProduct == null){
                return "";
            }
            return mProduct.getPrice().getCurrent() + " " + mProduct.getPrice().getCurrent();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }

    @Override
    public String getOriginalPrice() {
        try {
            if(mProduct == null){
                return "";
            }
            return mProduct.getPrice().getOriginal() + " " + mProduct.getPrice().getCurrent();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }

    @Override
    public String getBrand() {
        try {
            if(mProduct == null){
                return "";
            }
            return mProduct.getBrand();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }
}

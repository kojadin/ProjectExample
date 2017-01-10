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

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.erevacation.challenge.R;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.injection.scopes.PerViewHolder;
import com.erevacation.challenge.rxbus.events.ProductClickEvent;
import com.erevacation.challenge.ui.base.view.MvvmView;
import com.erevacation.challenge.ui.base.viewmodel.BaseViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import timber.log.Timber;

@PerViewHolder
public class ProductViewModel extends BaseViewModel<MvvmView> implements ProductMvvm.ViewModel {

    private Product mProduct;

    @Inject
    public ProductViewModel() {
    }

    @Override
    protected String getTag() {
        return ProductViewModel.class.getSimpleName();
    }

    @Override
    public void onProductClick(View view) {
        rxEventBus.post(new ProductClickEvent(mProduct, view.findViewById(R.id.product_image)));
    }

    @Override
    public void update(Product product) {
        this.mProduct = product;
        notifyChange();
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
            return mProduct.getPrice().getCurrent() + " " + mProduct.getPrice().getCurrency();
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
            return mProduct.getPrice().getOriginal() + " " + mProduct.getPrice().getCurrency();
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return "";
        }
    }

    @Override
    public int getOriginalPriceVisible() {
        try{
            if(Double.compare(mProduct.getPrice().getCurrent(), mProduct.getPrice().getOriginal()) == 0){
                return View.GONE;
            }
            return View.VISIBLE;
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return View.VISIBLE;
        }
    }

    @Override
    public boolean getOriginalPriceValid() {
        try{
            if(Double.compare(mProduct.getPrice().getCurrent(), mProduct.getPrice().getOriginal()) == 0){
                return true;
            }
            return false;
        }
        catch (Exception e){
            Timber.e(e, "can't set text");
            return false;
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

    @BindingAdapter({ "bind:frescoImageUrl" })
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        view.setImageURI(imageUrl);
    }

    @BindingAdapter({ "bind:isPriceValid" })
    public static void loadImage(TextView textView, boolean valid) {
        if(!valid){
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            textView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }
}

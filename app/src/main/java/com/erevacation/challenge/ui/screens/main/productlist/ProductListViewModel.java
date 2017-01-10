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

package com.erevacation.challenge.ui.screens.main.productlist;


import android.databinding.BindingAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.transition.TransitionInflater;

import com.erevacation.challenge.R;
import com.erevacation.challenge.anotations.RestoringKeysConstant;
import com.erevacation.challenge.anotations.SettingsConstants;
import com.erevacation.challenge.data.managers.product.ProductDataManager;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.rxbus.events.ProductClickEvent;
import com.erevacation.challenge.ui.base.viewmodel.BaseViewModel;
import com.erevacation.challenge.ui.screens.main.productdetails.ProductFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by kojadin on 12/15/16.
 */

public class ProductListViewModel extends BaseViewModel<IProductListMvvm.View> implements
        IProductListMvvm.ViewModel {

    @RestoringKeysConstant
    public static String PRODUCT_LIST_BUNDLE_KEY;

    static {
        SettingsConstants.populateConstants(ProductListViewModel.class);
    }

    ProductDataManager mProductDataManager;
    TreeSet<Product> mProductList = new TreeSet<>(new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            String obj1 = o1.getName();
            String obj2 = o2.getName();
            if (obj1 == null && obj1 == null) {
                return 0;
            }
            if (obj1 == null) {
                return -1;
            }
            if (obj1 == null) {
                return 1;
            }
            return obj1.compareTo(obj2);
        }
    });

    @Inject
    public ProductListViewModel(ProductDataManager productDataManager) {
        this.mProductDataManager = productDataManager;
    }

    @Override
    protected String getTag() {
        return ProductListViewModel.class.getSimpleName();
    }

    @Override
    public void attachView(@NonNull IProductListMvvm.View view,
            @Nullable Bundle savedInstanceState) {
        super.attachView(view, savedInstanceState);
        if (resources.getBoolean(R.bool.isTablet)) {
            // set different stuff
        }

        if (savedInstanceState == null) {
            getProducts();
        }
    }

    @Override
    public void getProducts() {
        compositeDisposable.add(mProductDataManager.getProductList()
                .subscribe(productList -> {
                    Observable.fromArray(productList.getItems())
                            .flatMapIterable(products1 -> products1)
                            .filter(product -> product.isActive())
                            .subscribe(product -> {
                                mProductList.add(product);
                            });
                    getView().setProductList(new ArrayList<>(mProductList));
                }, error -> {
                    Timber.e(error, "Can't fetch list");
                    Snackbar.make(getView().getRootView(), error.getMessage(), Snackbar.LENGTH_LONG)
                            .show();
                }));
    }

    @Override
    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return () -> {
            mProductList.clear();
            getView().clearProductList();
            getProducts();
        };
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        super.saveInstanceState(outState);
        outState.putParcelable(PRODUCT_LIST_BUNDLE_KEY, Parcels.wrap(mProductList));
    }

    @Override
    public void restoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.restoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mProductList = Parcels.unwrap(savedInstanceState.getParcelable(PRODUCT_LIST_BUNDLE_KEY));
            getView().setUpRecyclerView();
            getView().setProductList(new ArrayList<>(mProductList));
        }
    }

    @Override
    public void addObservables() {
        super.addObservables();
        compositeDisposable.add(rxEventBus.observable(ProductClickEvent.class)
                .subscribe(productClickEvent -> {

                    ProductFragment productFragment = ProductFragment
                            .newInstance(productClickEvent.getProduct());

                    // From some reason animation flickering, I would have to spend more time on this to make it to work proper
                    if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getView().setSharedElementReturnTransitionCustom(
                                TransitionInflater.from(activityContext)
                                        .inflateTransition(R.transition.change_image_transform));
                        getView().setExitTransitionCustom(
                                TransitionInflater.from(activityContext)
                                        .inflateTransition(android.R.transition.fade));


                        productFragment.setSharedElementEnterTransition(
                                TransitionInflater.from(activityContext)
                                        .inflateTransition(R.transition.change_image_transform));
                        productFragment.setEnterTransition(TransitionInflater.from(activityContext)
                                .inflateTransition(android.R.transition.fade));
                        navigator.replaceFragmentAndAddToBackStack(R.id.container, productFragment,
                                ProductFragment.class.getSimpleName(), null, productClickEvent.getView(),
                                productClickEvent.getProduct().getSku());
                    } else {
                        navigator.replaceFragmentAndAddToBackStack(R.id.container,
                                productFragment,
                                ProductFragment.class.getSimpleName(), null);
                    }
                }, error -> {
                    Timber.e(error, "Something went wrong");
                    Snackbar.make(getView().getRootView(), error.getMessage(), Snackbar.LENGTH_LONG)
                            .show();
                }));
    }

    @BindingAdapter({ "bind:setOnRefreshListener" })
    public static void setOnRefreshListener(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener listener) {
        view.setOnRefreshListener(listener);
    }
}

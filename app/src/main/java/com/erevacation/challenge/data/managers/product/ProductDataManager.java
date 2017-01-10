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

package com.erevacation.challenge.data.managers.product;

import android.content.Context;

import com.erevacation.challenge.data.local.product.ProductRepo;
import com.erevacation.challenge.data.managers.BaseDataManager;
import com.erevacation.challenge.data.managers.SchedulerTransformer;
import com.erevacation.challenge.data.model.ApiResponse;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.data.remote.GitApi;
import com.erevacation.challenge.injection.qualifier.AppContext;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by kojadin on 12/15/16.
 */

public class ProductDataManager extends BaseDataManager<ProductRepo> implements
        IProductDataManager {

    @Inject
    public ProductDataManager(@AppContext Context context, GitApi gitApi, ProductRepo productRepo) {
        super(context, gitApi, productRepo);
    }

    @Override
    public Observable<ApiResponse<List<Product>>> getProductList() {
        if (isOnline()) {
            return gitApi.getProductList()
                    .compose(SchedulerTransformer.applyNetworkSchedulers())
                    .flatMap(listApiResponse -> {
                        getRepository().saveProductsAsync(listApiResponse.getItems());
                        return Observable.just(listApiResponse);
                    });
        } else {
            return getProductsLocal();
        }
    }

    public Observable<ApiResponse<List<Product>>> getProductsLocal() {
        return Observable.fromArray(getRepository().getProducts(true))
                .compose(SchedulerTransformer.applyDBSchedulers())
                .flatMapIterable(list -> list)
                .toList().map((Function<List<Product>, ApiResponse<List<Product>>>) productList -> {
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setItems(productList);
                    return apiResponse;
                }).toObservable();
    }
}

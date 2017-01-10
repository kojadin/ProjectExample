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

package com.erevacation.challenge.data.local.product;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.erevacation.challenge.data.local.BaseRepo;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.injection.scopes.PerApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

@PerApplication
@SuppressLint("NewApi") // try-with-resources is backported by retrolambda
public class RealmProductRepo extends BaseRepo implements ProductRepo {

    @Inject
    public RealmProductRepo(Provider<Realm> realmProvider) {
        super(realmProvider);
    }

    @Nullable
    @Override
    public List<Product> getProducts(boolean detached) {
        try (Realm realm = realmProvider.get()) {
            RealmResults<Product> realmResults = realm.where(Product.class).findAll();
            List<Product> list = new ArrayList<>();
            list.addAll(realmResults.subList(0, realmResults.size()));
            if (detached && !realmResults.isEmpty()) {
                list.clear();
                list.addAll(realm.copyFromRealm(realmResults.subList(0, realmResults.size())));
            }
            return list;
        }
    }

    @Override
    public void saveProducts(List<Product> productList) {
        try (Realm realm = realmProvider.get()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(productList);
            realm.commitTransaction();
        }
    }

    @Override
    public void saveProductsAsync(List<Product> productList) {
        try (Realm realm = realmProvider.get()) {
            realm.executeTransactionAsync(
                    realm1 -> realm1.copyToRealmOrUpdate(productList),
                    null,
                    error -> Timber.e(error, "Can't store data to realm"));
        }
    }
}

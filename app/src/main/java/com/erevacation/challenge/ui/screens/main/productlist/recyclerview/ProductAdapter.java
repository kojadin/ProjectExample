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

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erevacation.challenge.R;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.injection.qualifier.ActivityContext;
import com.erevacation.challenge.injection.scopes.PerFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;


@PerFragment
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> mProductList = new ArrayList<>();

    private Context mContext;

    private Resources mResources;

    private HashSet<Integer> mAlreadyAnimated = new HashSet<>();

    @Inject
    public ProductAdapter(@ActivityContext Context context, Resources resources) {
        mContext = context;
        mResources = resources;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_item_product, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = mProductList.get(position);

        holder.viewModel().update(product);

        ViewCompat.setTransitionName(holder.getBinding().productImage, product.getSku());

        holder.executePendingBindings();
    }

    @Override
    public void onViewDetachedFromWindow(ProductViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return (mProductList == null) ? 0 : mProductList.size();
    }

    public void updateProductList(List<Product> productList) {
        int currentCount = getItemCount();

        if (currentCount == 0) {
            this.mProductList = productList;
            notifyDataSetChanged();
        } else {
            this.mProductList.addAll(productList);
            notifyItemRangeInserted(currentCount, mProductList.size());
        }
    }

    public void clearAdapter() {
        mProductList.clear();
        notifyDataSetChanged();
    }
}

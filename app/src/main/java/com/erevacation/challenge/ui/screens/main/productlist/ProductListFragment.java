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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erevacation.challenge.R;
import com.erevacation.challenge.anotations.RestoringKeysConstant;
import com.erevacation.challenge.anotations.SettingsConstants;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.databinding.FragmentSelectProductBinding;
import com.erevacation.challenge.ui.base.BaseFragment;
import com.erevacation.challenge.ui.decoration.ItemOffsetDecoration;
import com.erevacation.challenge.ui.screens.main.productlist.recyclerview.ProductAdapter;
import com.erevacation.challenge.ui.widget.MaterialMenuDrawable;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by kojadin on 12/15/16.
 */

public class ProductListFragment extends
        BaseFragment<FragmentSelectProductBinding, IProductListMvvm.ViewModel> implements
        IProductListMvvm.View {

    @RestoringKeysConstant
    public static String YEAR_BUNDLE_KEY;

    static {
        SettingsConstants.populateConstants(ProductListFragment.class);
    }

    @Inject
    ProductAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        fragmentComponent().inject(this);
        return setAndBindContentView(inflater, container, R.layout.fragment_select_product,
                savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView();
    }

    @Override
    protected String getFragmentTitle() {
        return resources.getString(R.string.users_list_text);
    }

    @Override
    protected MaterialMenuDrawable.IconState getIconState() {
        return MaterialMenuDrawable.IconState.BURGER;
    }

    /**
     * Set recycler view with adapter with products data
     */
    @Override
    public void setUpRecyclerView() {

        binding.recyclerviewYear.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R
                .dimen.item_offset);
        binding.recyclerviewYear.addItemDecoration(itemDecoration);

        binding.recyclerviewYear.setAdapter(mAdapter);
    }

    @Override
    public void setProductList(List<Product> productList) {
        mAdapter.updateProductList(productList);
        binding.rootView.setRefreshing(false);
    }

    @Override
    public void clearProductList() {
        mAdapter.clearAdapter();
    }

    @Override
    public View getRootView() {
        return binding.rootView;
    }

    @Override
    public void setExitTransitionCustom(Transition transition) {
        setExitTransition(transition);
    }

    @Override
    public void setSharedElementReturnTransitionCustom(Transition transition) {
        setSharedElementReturnTransition(transition);
    }
}

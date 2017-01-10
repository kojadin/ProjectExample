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
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erevacation.challenge.R;
import com.erevacation.challenge.anotations.RestoringKeysConstant;
import com.erevacation.challenge.anotations.SettingsConstants;
import com.erevacation.challenge.data.model.Product;
import com.erevacation.challenge.databinding.FragmentDetailsBinding;
import com.erevacation.challenge.ui.base.BaseFragment;
import com.erevacation.challenge.ui.widget.MaterialMenuDrawable;

import org.parceler.Parcels;


public class ProductFragment extends
        BaseFragment<FragmentDetailsBinding, IProductMvvm.ViewModel> implements
        IProductMvvm.View {

    @RestoringKeysConstant
    public static String PRODUCT_ID_KEY;

    static {
        SettingsConstants.populateConstants(ProductFragment.class);
    }

    private Product mUser;

    public static ProductFragment newInstance(Product user) {
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_ID_KEY, Parcels.wrap(user));
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        fragmentComponent().inject(this);
        return setAndBindContentView(inflater, container, R.layout.fragment_details,
                savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mUser = Parcels.unwrap(getArguments().getParcelable(PRODUCT_ID_KEY));
            viewModel.update(mUser);
        }
        ViewCompat.setTransitionName(binding.profileDetailsImage, mUser.getSku());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected MaterialMenuDrawable.IconState getIconState() {
        return MaterialMenuDrawable.IconState.ARROW;
    }

    @Override
    protected String getFragmentTitle() {
        return resources.getString(R.string.product_details_text);
    }

    @Override
    public View getRootView() {
        return binding.rootView;
    }
}

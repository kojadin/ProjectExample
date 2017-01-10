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

package com.erevacation.challenge.ui.screens.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.erevacation.challenge.R;
import com.erevacation.challenge.databinding.ActivityMainBinding;
import com.erevacation.challenge.ui.base.BaseActivity;
import com.erevacation.challenge.ui.screens.main.productlist.ProductListFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements
        MainMvvm.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);
        setAndBindContentView(R.layout.activity_main, savedInstanceState);

        setSupportActionBar(binding.toolbar);
        setNavigationIcon(binding.toolbar);

        if (savedInstanceState == null) {
            navigator.replaceFragment(R.id.container, new ProductListFragment(),
                    ProductListFragment.class.getSimpleName(), null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                switch (mMaterialMenu.getIconState()) {
                    case ARROW:
                        onBackPressed();
                        break;
                    default:
                        break;
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}

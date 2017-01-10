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

package com.erevacation.challenge.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.erevacation.challenge.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class MosaicRecyclerView extends RecyclerView {

    private ArrayList<Integer> mMosaicPattern;

    public MosaicRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public MosaicRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ArrayList<Integer> getMosaicPattern() {
        return mMosaicPattern;
    }

    public void setMosaicPattern(ArrayList<Integer> mosaicPattern) {
        mMosaicPattern = mosaicPattern;
    }

    private void init(final Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MosaicRecyclerView);
        try {
            mMosaicPattern = new ArrayList<>();
            Observable.just(new ArrayList<>(
                    Arrays.asList(
                            arr.getString(R.styleable.MosaicRecyclerView_repeatArray).split(","))))
                    .flatMapIterable(stringArray -> stringArray)
                    .doOnNext(string -> mMosaicPattern.add(Integer.parseInt(string)))
                    .subscribe();
        } catch (Exception e) {
            Timber.d("Don't worry, you forgot to define repeat array for mosaic recycler view");
            mMosaicPattern = new ArrayList<>(Arrays.asList(new Integer[]{ 1, 1, 1 }));
        }
        arr.recycle();

        setUpMosaicView();
    }

    private void setUpMosaicView() {
        int columnNumberInGrid = 1;
        for (Integer num : mMosaicPattern) {
            columnNumberInGrid *= num;
        }
        GridLayoutManager
                productGridLayoutManager = new GridLayoutManager(getContext(), columnNumberInGrid);
        int finalColumnNumberInGrid = columnNumberInGrid;
        productGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int elementNumberForRepeat = 0;
                for (Integer num : mMosaicPattern) {
                    elementNumberForRepeat += num;
                }
                int size;
                int inLine = getInLine(position, elementNumberForRepeat, mMosaicPattern);
                int elementCountInLine = mMosaicPattern.get(inLine);
                size = finalColumnNumberInGrid / elementCountInLine;
                return size;
            }
        });

        productGridLayoutManager.getSpanSizeLookup().setSpanIndexCacheEnabled(true);
        setLayoutManager(productGridLayoutManager);
    }

    /**
     * It calculate every 3 line ( e.g. getInLine(position, 7, {2,3,2}) )
     * -------------  -------------
     * |           |  |           |
     * |     0     |  |    1      |
     * |           |  |           |
     * |           |  |           |
     * -------------  -------------
     * --------  --------  --------
     * |      |  |      |  |      |
     * |   2  |  |   3  |  |   4  |
     * --------  --------  --------
     * -------------  -------------
     * |           |  |           |
     * |     5     |  |    6      |
     * |           |  |           |
     * |           |  |           |
     * -------------  -------------
     *
     * @param position
     * @param count    - number of object in first 3 lines
     * @return line index for objec on position
     */
    private int getInLine(int position, int count, List<Integer> elements) {
        int repeatPosition = position % count;
        int inLine = 0;
        int limit = 0;
        for (Integer num : elements) {
            limit += num;
            if (repeatPosition < limit) {
                return inLine;
            }
            inLine++;
        }
        if (inLine >= elements.size()) {
            Timber.e("Invalid inline " + inLine + ", size is " + elements.size() + ", count is " +
                    count +
                    ", position is " + position + " elements " +
                    TextUtils.join(",", elements));
        }
        return inLine;
    }

}

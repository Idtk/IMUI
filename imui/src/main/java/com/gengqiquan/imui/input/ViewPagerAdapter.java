/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gengqiquan.imui.input;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author gengqiquan
 * @date 2018/4/12 上午10:04
 */
public class ViewPagerAdapter<T> extends PagerAdapter {

    /**
     * The m context.
     */
    private Context mContext;

    private ArrayList<View> mViews = new ArrayList<>();
    private List mList = new ArrayList<T>();
    private int mItemLayoutId;
    private ViewBind<T> mViewBind;


    private NotifyDataSetChangedListener notifyDataSetChangedListener;

    public ViewPagerAdapter(Context context) {
        this.mContext = context;
    }

    public ViewPagerAdapter<T> list(List list) {
        this.mList = list;
        return this;
    }

    public ViewPagerAdapter<T> layoutId(int layoutId) {
        this.mItemLayoutId = layoutId;
        return this;
    }

    public ViewPagerAdapter<T> bindViewData(ViewBind<T> viewBind) {
        this.mViewBind = viewBind;
        return this;
    }

    public ViewPagerAdapter<T> notifyDataSetChangedListener(NotifyDataSetChangedListener notifyDataSetChangedListener) {
        this.notifyDataSetChangedListener = notifyDataSetChangedListener;
        return this;
    }

    /**
     * 描述：获取数量.
     *
     * @return the count
     * @see PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 描述：Object是否对应这个View.
     *
     * @return true, if is view from object
     * @see PagerAdapter#isViewFromObject(View, Object)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 描述：显示View.
     *
     * @param container the container
     * @param position  the position
     * @return the object
     * @see PagerAdapter#instantiateItem(View, int)
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v;
        if (mViews.isEmpty()) {
            v = LayoutInflater.from(container.getContext()).inflate(mItemLayoutId, container, false);
        } else {
            v = mViews.remove(0);
        }
        container.addView(v);
        if (mViewBind != null) {
            mViewBind.convert(v, (T) mList.get(position), position);
        }
        return v;
    }

    /**
     * 描述：移除View.
     *
     * @param container the container
     * @param position  the position
     * @param object    the object
     * @see PagerAdapter#destroyItem(View, int, Object)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        if (object != container)
            mViews.add((View) object);
    }

    /**
     * 描述：很重要，否则不能notifyDataSetChanged.
     *
     * @param object the object
     * @return the item position
     * @see PagerAdapter#getItemPosition(Object)
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (notifyDataSetChangedListener != null) {
            notifyDataSetChangedListener.notifyChanged();
        }
    }

    public interface ViewBind<T> {
        void convert(View v, T item, int position);

    }

    public interface NotifyDataSetChangedListener {
        void notifyChanged();

    }
}

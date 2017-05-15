package com.example.fy.blog.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by fy on 2016/4/2.
 */
public class ViewPageFragmentAdapter extends FragmentPagerAdapter
implements ViewPager.OnPageChangeListener{

    private final Context mContext;
    protected PagerSlidingTabStrip mPagerStrip;
    private final ViewPager mViewPager;
    private final ArrayList<ViewPageInfo> mTabs = new ArrayList<ViewPageInfo>();




    public ViewPageFragmentAdapter(FragmentManager fm, PagerSlidingTabStrip pageStrip,ViewPager pager) {
        super(fm);
        mContext = pager.getContext();
        mPagerStrip = pageStrip;
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
        mPagerStrip.setViewPager(mViewPager);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for(ViewPageInfo viewPageInfo: mTabs){
            TextView v = (TextView) LayoutInflater.from(mContext).inflate(R.layout.sliding_tab_item,null);
            v.setText(viewPageInfo.title);
            mPagerStrip.addTab(v);
        }
    }

    public void addTab(String title, String tag, Class<?> clss, Bundle args){
        ViewPageInfo info = new ViewPageInfo(title,tag,clss,args);
        mTabs.add(info);
    }

    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext,info.clss.getName(),info.args);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTabs.get(position).title;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public int getCount() {
        return mTabs.size();
    }
}

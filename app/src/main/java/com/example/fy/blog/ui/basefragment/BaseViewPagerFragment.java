package com.example.fy.blog.ui.basefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fy.blog.R;
import com.example.fy.blog.adapter.ViewPageFragmentAdapter;
import com.example.fy.blog.widget.PagerSlidingTabStrip;

/**
 * Created by fy on 2016/3/20.
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter mTabsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_viewpage_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabStrip = (PagerSlidingTabStrip)view.findViewById(R.id.pager_tabstrip);

        mViewPager = (ViewPager)view.findViewById(R.id.pager);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),mTabStrip,mViewPager);

        onSetupAdapter(mTabsAdapter);

        mTabsAdapter.notifyDataSetChanged();
        mViewPager.setOffscreenPageLimit(3);
        if(null != savedInstanceState){
            int pos = savedInstanceState.getInt("position");
            mViewPager.setCurrentItem(pos,true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",mViewPager.getCurrentItem());
    }

    protected abstract void onSetupAdapter(ViewPageFragmentAdapter adapter);
}

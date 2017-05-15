package com.example.fy.blog.ui.fragments;

import android.os.Bundle;

import com.example.fy.blog.AppContext;
import com.example.fy.blog.R;
import com.example.fy.blog.adapter.ViewPageFragmentAdapter;
import com.example.fy.blog.ui.basefragment.BaseViewPagerFragment;
import com.example.fy.blog.util.Contasts;
import com.example.fy.blog.util.ShareUtils;

/**
 * Created by fy on 2016/4/4.
 */
public class MySelfViewPagerFragment extends BaseViewPagerFragment {
    public static MySelfViewPagerFragment newInstance() {
        return new MySelfViewPagerFragment();
    }

    @Override
    protected void onSetupAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(R.array.myself_title_array);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Contasts.USER, ShareUtils.getLoginInfo());
        //将用户信息存储在bundle中，然后根据用户信息加载如下的fragment
        adapter.addTab(title[0],"myblog",MyselfBlogFragment.class,null);
        adapter.addTab(title[1],"mycollect",MyselfCollectFragment.class,null);
        adapter.addTab(title[2],"mycomment",MyselfCommentFragment.class,null);
    }
}

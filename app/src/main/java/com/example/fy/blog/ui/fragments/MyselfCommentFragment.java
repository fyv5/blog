package com.example.fy.blog.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.example.fy.blog.adapter.CommonAdapter;
import com.example.fy.blog.bean.Entity;

import java.util.List;

/**
 * Created by fy on 2016/4/5.
 */
public class MyselfCommentFragment extends BaseSwipeRefreshFragment{
    private boolean isPrepared;

    public MyselfCommentFragment newInstance(){
        return new MyselfCommentFragment();
    }
    //fragment的懒加载
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isPrepared = true;
        lazyLoad();
        return view;
    }
    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible){
            return;
        }
        requestData();
    }

    @Override
    protected void requestData() {

    }

    @Override
    public CommonAdapter getAdapter() {
        return null;
    }


    @Override
    protected List getDatas(String response) {
        return null;
    }


    @Override
    protected void onItemClick(int position, Entity data) {

    }
}

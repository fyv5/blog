package com.example.fy.blog.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fy.blog.R;
import com.example.fy.blog.adapter.BlogAdapter;
import com.example.fy.blog.adapter.CommonAdapter;
import com.example.fy.blog.bean.Blog;
import com.example.fy.blog.bean.Entity;
import com.example.fy.blog.ui.BlogDetailActivity;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.util.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2016/4/5.
 */
public class MyselfCollectFragment extends BaseSwipeRefreshFragment{

    private boolean isPrepared;

    public MyselfCollectFragment newInstance(){
        return new MyselfCollectFragment();
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
        String username = ShareUtils.getUsername();
        APIUtils.getMyselfCollect(mcurrentPage,username,mHandler);
    }

    @Override
    public CommonAdapter getAdapter() {
        return new BlogAdapter(getContext(), R.layout.list_item_blog);
    }


    @Override
    protected List getDatas(String response) {
        Gson gson = new Gson();
        List<Blog> list = new ArrayList<>();
        try{
            list = gson.fromJson(response,new TypeToken<List<Blog>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onItemClick(int position, Entity data) {
        //Toast.makeText(getContext(),"显示我收藏的博客细节",Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Blog",data);
        Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

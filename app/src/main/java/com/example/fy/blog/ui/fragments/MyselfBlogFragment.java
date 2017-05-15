package com.example.fy.blog.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fy.blog.R;
import com.example.fy.blog.adapter.BlogAdapter;
import com.example.fy.blog.adapter.CommonAdapter;
import com.example.fy.blog.ui.BlogDetailActivity;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.bean.Blog;
import com.example.fy.blog.util.LogUtil;
import com.example.fy.blog.util.ShareUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2016/4/5.
 */
public class MyselfBlogFragment extends BaseSwipeRefreshFragment<Blog> {


    private boolean isPrepared;
    public static MyselfBlogFragment newInstance(){
        return new MyselfBlogFragment();
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
    protected List<Blog> getDatas(String response) {
        //返回对象集合
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
    protected void onItemClick(int position, Blog data) {
        LogUtil.d("MyselfBlogFragment","显示我的博客细节");
        //Toast.makeText(getContext(),"显示我的博客细节",Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Blog",data);
        Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void requestData() {
        String username = ShareUtils.getUsername();
        APIUtils.getBlog(mcurrentPage,username,mHandler);
    }

    @Override
    public CommonAdapter<Blog> getAdapter() {
        return new BlogAdapter(getActivity(),R.layout.list_item_blog);
    }
}

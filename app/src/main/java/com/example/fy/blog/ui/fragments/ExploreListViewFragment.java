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
import com.example.fy.blog.ui.BlogDetailActivity;
import com.example.fy.blog.util.APIUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2016/4/4.
 */
public class ExploreListViewFragment extends BaseSwipeRefreshFragment<Blog> {


    public static ExploreListViewFragment newInstance(){
            return new ExploreListViewFragment();
    }

    @Override
    protected void lazyLoad() {

    }


    //fragment的懒加载
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        requestData();
        return view;
    }
    @Override
    protected List<Blog> getDatas(String response) {
        Gson gson = new Gson();
        List<Blog> list = new ArrayList<>();
        try {
            list = gson.fromJson(response,new TypeToken<List<Blog>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void requestData() {
        //获取网络数据
        APIUtils.getBlog(mcurrentPage,mHandler);
    }

    @Override
    public CommonAdapter<Blog> getAdapter() {
        return new BlogAdapter(getActivity(), R.layout.list_item_blog);
    }

    @Override
    protected void onItemClick(int position, Blog data) {
        //显示博客细节
        //Toast.makeText(getContext(),"显示博客细节",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Blog",data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

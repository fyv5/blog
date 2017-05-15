package com.example.fy.blog.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.bean.Blog;
import com.example.fy.blog.bean.User;

/**
 * Created by fy on 2016/6/10.
 */
public class BlogDetailHeaderFragment extends Fragment {

    TextView tv_title;
    TextView tv_author;
    TextView tv_time;
    TextView tv_allcontent;
    TextView tv_content;
    Blog blog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.blog_detail_header,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        blog = (Blog)arguments.getSerializable("Blog");
        initView(view);
    }

    private void initView(View view) {
        tv_title = (TextView)view.findViewById(R.id.title);
        tv_time = (TextView)view.findViewById(R.id.time);
        tv_author = (TextView)view.findViewById(R.id.author);
        tv_content = (TextView)view.findViewById(R.id.content);
        tv_allcontent = (TextView)view.findViewById(R.id.allcontent);
        tv_title.setText(blog.getTitle());
        tv_time.setText(blog.getTime());
        tv_author.setText(blog.getAuthor().get_username());
        tv_allcontent.setText("全部评论");
        tv_content.setText(blog.getContent());
    }
}

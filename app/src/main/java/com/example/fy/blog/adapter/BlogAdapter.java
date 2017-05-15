package com.example.fy.blog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.fy.blog.R;
import com.example.fy.blog.bean.Blog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 首页列表适配器
 * Created by fy on 2016/4/8.
 */
public class BlogAdapter extends CommonAdapter<Blog> {

    private static final String TAG = "BlogAdapter";
    public BlogAdapter(Context context, int layoutId) {
        super(context, layoutId);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    protected void convert(ViewHolder vh, Blog item) {
        //加载博客标题
        vh.setText(R.id.tv_title,item.getTitle());
        //加载博客的介绍
        vh.setText(R.id.tv_description,item.getDescription(),R.string.msg_project_empty_description);
        //加载用户头像
        imageLoader.displayImage(item.getAuthor().get_portrait(),(ImageView)vh.getView(R.id.iv_portrait),options);
//        vh.setImageResource(R.id.iv_portrait,R.drawable.mini_avatar);
//        String portraitURL = item.getAuthor().getNew_portrait();
//        if(portraitURL.endsWith("portrait.gif")){//显示默认头像
//            vh.setImageResource(R.id.iv_portrait,R.drawable.mini_avatar);
//        }else{//显示个人头像
//            vh.setImageForUrl(R.id.iv_portrait,portraitURL);
//        }
        //为头像设置监听器，点击时显示用户信息
        vh.getView(R.id.iv_portrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示头像,待完成
//                Toast.makeText(mContext,"显示用户信息",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: 显示用户信息");
            }
        });
    }

}

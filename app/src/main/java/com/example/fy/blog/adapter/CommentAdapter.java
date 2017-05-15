package com.example.fy.blog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.bean.Comment;
import com.example.fy.blog.util.LogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by fy on 2016/6/11.
 */
public class CommentAdapter extends BaseAdapter {

    private List<Comment> mData;
    private LayoutInflater mInflater;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.mini_avatar)
            .showImageOnFail(R.drawable.loadingfailed)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    public CommentAdapter(Context context,List<Comment> data){
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder holder = null;
        if(convertView == null){
            holder = new ViewHoder();
            convertView = mInflater.inflate(R.layout.list_item_comment,null);
            holder.avatar = (ImageView)convertView.findViewById(R.id.avatar);
            holder.username = (TextView)convertView.findViewById(R.id.name);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            holder.content = (TextView)convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHoder)convertView.getTag();
        }
        //使用ImageLoader加载用户头像
        LogUtil.d("CommentAdapter",mData.get(position).getId());
        imageLoader.displayImage(mData.get(position).getAvatarUrl(),holder.avatar,options);
        //设置评论Item的名字、时间、内容
        holder.username.setText(mData.get(position).getUsername());
        holder.time.setText(mData.get(position).getTime());
        holder.content.setText(mData.get(position).getContent());
        return convertView;
    }
    public final class ViewHoder{
        public ImageView avatar;
        public TextView username;
        public TextView time;
        public TextView content;
    }
}

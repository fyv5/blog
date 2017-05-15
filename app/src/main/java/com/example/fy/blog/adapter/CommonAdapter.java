package com.example.fy.blog.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.fy.blog.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2016/4/5.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    //ImageLoader加载时的options（选项）
    public DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.mini_avatar)
            .showImageOnFail(R.drawable.loadingfailed)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    public  ImageLoader imageLoader;
    public CommonAdapter(Context context,int layoutId){
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<T>();
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh = ViewHolder.getViewHolder(this.mContext,convertView,parent,
                this.mLayoutId,position);
        convert(vh, getItem(position));
        return vh.getmConvertView();
    }

    public void addItem(List<T> items){
        checkListNull();
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    private void checkListNull() {
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
    }

    // 提供给外部填充实际的显示数据，以及可以一些其他的操作，如：隐藏＝＝
    protected abstract void convert(ViewHolder vh, T item);

    public void clear(){
        if(mDatas == null || mDatas.isEmpty()) return;
        mDatas.clear();
        notifyDataSetChanged();
    }
}

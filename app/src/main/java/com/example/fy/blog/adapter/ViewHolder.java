package com.example.fy.blog.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.common.StringUtils;

/**
 * 通用性极好的ViewHolder
 * Created by fy on 2016/4/5.
 */
public class ViewHolder {
    //用于存储listView item 的容器
    private SparseArray<View> mViews;

    //item的根View
    private View mConvertView;
    protected Context mContext;
    private int position;

    public ViewHolder(Context context, ViewGroup parent,int layoutId,int position){
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        this.mConvertView.setTag(this);
        this.mContext = context;
        this.position = position;
    }

    /**
     *获取一个ViewHolder
     *
     * @param context context
     * @param convertView view
     * @param parent parent view
     * @param layoutId 布局资源id
     * @param position 索引
     * @return
     */
    public static ViewHolder getViewHolder(Context context,View convertView,ViewGroup parent,
                                           int layoutId,int position){
        if(convertView == null){
            return new ViewHolder(context,parent,layoutId,position);
        }
        return (ViewHolder) convertView.getTag();
    }
    //通过一个viewId获得一个view
    public <T extends View> T getView(int viewId){

        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
    public int getPosition(){
        return this.position;
    }
    //返回viewholder的容器
    public View getmConvertView(){
        return this.mConvertView;
    }

    //给TextView设置文字
    public void setText(int viewId,String text){
        if(StringUtils.isEmpty(text)) return;
        TextView tv = getView(viewId);//获得viewId指定view，先从SparseArray获取如果没有就findViewById
        tv.setText(text);
    }
    //给写博客介绍设置文字内容，有介绍就设为text，没有介绍就为emptyRes的提示信息
    public void setText(int viewId, String text, int emptyRes) {
        TextView tv = getView(viewId);
        if (StringUtils.isEmpty(text)) {
            tv.setText(emptyRes);
        } else {
            tv.setText(text);
        }
    }

    // 给ImageView设置图片资源
    public void setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
    }

}

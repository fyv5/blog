package com.example.fy.blog.adapter;

import android.os.Bundle;

/**
 * Created by fy on 2016/4/3.
 */
public final class ViewPageInfo {
    public final String tag;
    public final Class<?> clss;
    public final Bundle args;
    public final String title;

    public ViewPageInfo(String _title,String _tag,Class<?> _class,Bundle _args){
        title = _title;
        tag = _tag;
        clss = _class;
        args = _args;
    }
}

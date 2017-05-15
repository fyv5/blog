package com.example.fy.blog.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fy on 2016/4/5.
 */
public class CBSwipeRefreshLayout extends SwipeRefreshLayout {
    public CBSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CBSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isEnabled())
            return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isEnabled())
            return false;
        return super.onTouchEvent(ev);
    }
}

package com.example.fy.blog.interfaces;

import android.graphics.Bitmap;

/**
 * Created by fy on 2016/6/7.
 */
public interface OnGetPortraitCallback {
    void OnGetPortraitSuccess(Bitmap bitmap);
    void OnGetPortraitFailed();
}

package com.example.fy.blog.interfaces;

/**
 * Created by fy on 2016/6/1.
 */
public interface OnBlogCallback {
    void OnGetBlogSuccess(String response);
    void OnGetBlogFailed();
}

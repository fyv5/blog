package com.example.fy.blog.interfaces;

import com.example.fy.blog.bean.Comment;

/**
 * Created by fy on 2016/6/11.
 */
public interface OnGetCommentCallback {
    void OnGetCommentSuccess(String response);
    void OnGetCommentFailed();
}

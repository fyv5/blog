package com.example.fy.blog.interfaces;


import com.example.fy.blog.bean.LoginEntity;

/**
 * Created by fy on 2016/3/27.
 */
public interface OnLoginCallback {
    void onLoginSuccess(LoginEntity entity);
    void onLoginFailed(int type,String msg);
}

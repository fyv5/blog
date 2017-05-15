package com.example.fy.blog;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.fy.blog.bean.User;
import com.example.fy.blog.util.Contasts;
import com.example.fy.blog.util.LogUtil;
import com.example.fy.blog.util.ShareUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Properties;

/**
 * Created by fy on 2016/3/27.
 */
public class AppContext extends Application{
    private static Context context;
    public static AppContext appContext;
    private boolean login = false;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appContext = this;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
    public static Context getContext() {
        return context;
    }
    public static AppContext getIntance(){
        //双重检查锁定单例模式，但是没有必要，因为该实例在oncrete中只能创建一次
        /*if(appContext == null){
            synchronized (AppContext.class){
                if(appContext == null){
                    appContext = new AppContext();
                }
            }
        }*/
        return appContext;
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public boolean isLogin(){
        return login;
    }

}

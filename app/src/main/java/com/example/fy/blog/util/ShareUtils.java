package com.example.fy.blog.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fy.blog.AppContext;
import com.example.fy.blog.bean.User;

/**
 * Created by fy on 2016/3/27.
 */
public class ShareUtils {
    private static final String TAG = "ShareUtils";
    /**
     * 写入String格式的封装
     * @param file
     * @param key
     * @param value
     */
    private static void write(String file,String key,String value) {
        try{
            SharedPreferences preferences = AppContext.getContext().
                getSharedPreferences(file, Context.MODE_PRIVATE);
            preferences.edit().putString(key,value).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 写入int类型的封装
     * @param file
     * @param key
     * @param value
     */
    private static void write(String file,String key,int value) {

    }

    /**
     * 写入boolean类型的封装
     * @param file
     * @param key
     * @param state
     */
    private static void write(String file,String key,boolean state){
        try{
            SharedPreferences preferences = AppContext.getContext().
                    getSharedPreferences(file, Context.MODE_PRIVATE);
            preferences.edit().putBoolean(key,state).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取String类型的参数
     * @param file
     * @param key
     * @return
     */
    private static String read(String file,String key) {
        SharedPreferences preferences = AppContext.getContext().
                getSharedPreferences(file,Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }


    public static void putCookies(String value) {
        write(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_COOKIES,value);
    }

    public static String getCookies() {
        return read(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_COOKIES);
    }

    public static String getUsername(){
        return read(Contasts.SHARE_FILE_COOKIE,Contasts.KEY_USERNAME);
    }
    public static String getPassword(){
        return read(Contasts.SHARE_FILE_COOKIE,Contasts.KEY_PASSWORD);
    }
    public static void putUserInfo(String username,String password){
        write(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_USERNAME,username);
        write(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_PASSWORD,password);
    }
    public static String getUserInfo(){
        return read(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_USERNAME)+" "+
                read(Contasts.SHARE_FILE_COOKIE, Contasts.KEY_PASSWORD);

    }
    public static void putLoginState(boolean state){
        write(Contasts.SHARE_FILE_COOKIE,Contasts.KEY_LOGIN_STATE,state);
    }
    public static boolean getLoginState(){
        SharedPreferences preferences = AppContext.getContext().
                getSharedPreferences(Contasts.SHARE_FILE_COOKIE,Context.MODE_PRIVATE);
        return preferences.getBoolean(Contasts.KEY_LOGIN_STATE,false);
    }
    //写入登录信息
    public static void putLoginInfo(User user){
        write(Contasts.SHARE_FILE_COOKIE,Contasts.KEY_USERNAME,user.get_username());
        write(Contasts.SHARE_FILE_COOKIE,Contasts.PROP_KEY_PORTRAIT,user.get_portrait());
        write(Contasts.SHARE_FILE_COOKIE,Contasts.PROP_KEY_NEWPORTRAIT,user.getNew_portrait());
    }
    //得到登录信息
    public static User getLoginInfo(){
        String username = read(Contasts.SHARE_FILE_COOKIE,Contasts.KEY_USERNAME);
        String portrait = read(Contasts.SHARE_FILE_COOKIE,Contasts.PROP_KEY_PORTRAIT);
        String new_portrait = read(Contasts.SHARE_FILE_COOKIE,Contasts.PROP_KEY_NEWPORTRAIT);
        return new User(username,portrait,new_portrait);
    }

}

package com.example.fy.blog;

import android.content.Context;

import com.example.fy.blog.bean.User;
import com.example.fy.blog.util.Contasts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by fy on 2016/5/31.
 */
public class AppConfig {

    public static final String APP_CONFIG = "config";
    private boolean login = false;

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context){
        if(appConfig == null){
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    public void set(Properties ps){
        Properties props = get();
        props.putAll(props);
        setProps(ps);
    }

    public String get(String key){
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }
    public void setProps(Properties props) {
        FileOutputStream fos = null;
        try{
            //把config建在（自定义）app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG,Context.MODE_PRIVATE);
            File conf = new File(dirConf,APP_CONFIG);
            fos = new FileOutputStream(conf);
            props.store(fos,null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public Properties get(){
        FileInputStream fis = null;
        Properties props = new Properties();
        try{
            //读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG,Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath()+File.separator+APP_CONFIG);
            props.load(fis);//从输入流中读取键和元素对

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return props;
    }


}

package com.example.fy.blog.ui;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fy.blog.AppContext;
import com.example.fy.blog.R;
import com.example.fy.blog.bean.User;
import com.example.fy.blog.interfaces.DrawerMenuCallBack;
import com.example.fy.blog.interfaces.OnGetPortraitCallback;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.util.ShareUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by fy on 2016/3/20.
 */
public class DrawerNavigationMenu extends Fragment{

    private static final String TAG = "DrawerNavigationMenu";
    private DrawerMenuCallBack mCallBack;
    RelativeLayout menuUserLayout;
    LinearLayout menuUserInfoLayout;
    LinearLayout menuFirstPageLayout;
    LinearLayout menuMyselfLayout;
    LinearLayout menuSettingLayot;
    LinearLayout menuUserInfoLoginTipsLayout;
    ImageView ivPortrait;
    TextView tvNmae;
    Bitmap avator;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_drawer_menu,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuUserLayout = (RelativeLayout)view.findViewById(R.id.menu_user_layout);
        menuUserInfoLayout = (LinearLayout) view.findViewById(R.id.menu_user_info_layout);
        menuFirstPageLayout = (LinearLayout)view.findViewById(R.id.meun_item_firstpage);
        menuMyselfLayout = (LinearLayout)view.findViewById(R.id.meun_item_myblog) ;
        menuSettingLayot = (LinearLayout)view.findViewById(R.id.meun_item_set);
        menuUserInfoLoginTipsLayout = (LinearLayout)view.findViewById(R.id.menu_user_info_login_tips_layout);
        ivPortrait = (ImageView)view.findViewById(R.id.iv_portrait);
        tvNmae = (TextView)view.findViewById(R.id.tv_name);
        menuFirstPageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFirst();
            }
        });
        menuMyselfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMyself();
            }
        });
        menuSettingLayot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSetting();
            }
        });
        menuUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ShareUtils.getLoginState()){
                    onClickLogin();
                }else{
                    Intent intent = new Intent(getActivity(),UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        //开启app时初始化一次登录位置的状态
        setUserView();
    }

    //登录时的广播
    private BroadcastReceiver mUserChangeReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setUserView();
        }
    };
    //登出后的广播,将头像和姓名还原为默认值
    private BroadcastReceiver mUserSignoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ivPortrait.setImageResource(R.drawable.mini_avatar);
            tvNmae.setText("");
            menuUserInfoLayout.setVisibility(View.GONE);
            menuUserInfoLoginTipsLayout.setVisibility(View.VISIBLE);
            return;
        }
    };
    public void setUserView() {
        //判断是否已经登录，如果登录则显示用户的头像和信息
        if(!ShareUtils.getLoginState()){
            ivPortrait.setImageResource(R.drawable.mini_avatar);
            tvNmae.setText("");
            menuUserInfoLayout.setVisibility(View.GONE);
            menuUserInfoLoginTipsLayout.setVisibility(View.VISIBLE);
            return;
        }
        menuUserInfoLayout.setVisibility(View.VISIBLE);
        menuUserInfoLoginTipsLayout.setVisibility(View.GONE);
        //显示用户头像和用户名
//        User user = AppContext.getIntance().getLoginInfo();
//        Log.d(TAG, "setUserView: "+user.get_portrait());
        User user = ShareUtils.getLoginInfo();
        String portrait = user.get_portrait() == null||user.get_portrait().equals("null")?"":user.get_portrait();
        //加载用户头像
        ImageLoader.getInstance().displayImage(portrait,ivPortrait);
        ivPortrait.setImageBitmap(avator);

        tvNmae.setText(user.get_username());
        Log.d(TAG, "setUserView: "+user.get_portrait());
        Log.d(TAG, "setUserView: 用户状态更新成功");
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        menuUserInfoLayout = getLayoutInflater();

        if(activity instanceof DrawerMenuCallBack){
            mCallBack = (DrawerMenuCallBack)activity;
        }
        //注册一个用户信息改变的广播
        IntentFilter intentFilter = new IntentFilter("com.example.blog.LOGIN");
        activity.registerReceiver(mUserChangeReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("com.example.blog.SIGNOUT");
        activity.registerReceiver(mUserSignoutReceiver,intentFilter1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallBack = null;
        try{
            getActivity().unregisterReceiver(mUserChangeReceiver);
            getActivity().unregisterReceiver(mUserSignoutReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void onClickLogin() {
        if (mCallBack != null) {
            mCallBack.onClickLogin();
        }
    }
    private void onClickFirst(){
        if(mCallBack != null){
            mCallBack.onClickExplore();
        }
    }
    private void onClickMyself(){
        if(mCallBack != null){
            mCallBack.onClickMyself();
        }
    }
    private void onClickSetting(){
        if(mCallBack != null){
            mCallBack.onClickSetting();
        }
    }


}

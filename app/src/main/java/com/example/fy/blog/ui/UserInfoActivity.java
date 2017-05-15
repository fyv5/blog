package com.example.fy.blog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.example.fy.blog.R;
import com.example.fy.blog.util.ShareUtils;

/**
 * Created by fy on 2016/6/7.
 */
public class UserInfoActivity extends BaseActivity {
    Button button;
    ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        initView();
    }

    private void initView() {
        actionBar = getSupportActionBar();
        actionBar.setTitle("个人信息");
        button = (Button)findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.putLoginState(false);
                sendBroadcast(new Intent("com.example.blog.SIGNOUT"));
                finish();
            }
        });
    }
}

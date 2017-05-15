package com.example.fy.blog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fy.blog.R;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.bean.LoginEntity;
import com.example.fy.blog.interfaces.OnLoginCallback;
import com.example.fy.blog.interfaces.OnRegisterCallback;
import com.example.fy.blog.util.ShareUtils;

/**
 * Created by fy on 2016/5/29.
 */
public class RegisterActivity extends BaseActivity {
    private EditText etname;
    private EditText etpass1;
    private EditText etpass2;
    private Button register;
    private String username,password1,password2;
    private static final String TAG = "RegisterActivity";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.register);
        initView();
    }

    private void initView() {
        etname = (EditText)findViewById(R.id.username_register);
        etpass1 = (EditText)findViewById(R.id.password1);
        etpass2 = (EditText)findViewById(R.id.password2);
        register = (Button)findViewById(R.id.register_affirm);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void handleRegister() {
        int code = enableRegister();
        if(code == 0){
            APIUtils.register(username, password1, new OnRegisterCallback() {
                @Override
                public void OnRegisterSuccess() {

                    APIUtils.login(username, password1, new OnLoginCallback() {
                        @Override
                        public void onLoginSuccess(LoginEntity entity) {
                            Log.d(TAG, "onLoginSuccess: 注册之后登陆成功");
                        }

                        @Override
                        public void onLoginFailed(int type, String msg) {
                            Log.d(TAG, "onLoginFailed: 注册之后登陆失败");
                        }
                    });
                    //注册之后登陆
                    ShareUtils.putUserInfo(username,password1);
                    //AppContext.getIntance().saveLoginInfo(new User(username,null,null));
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                    Log.d(TAG, "OnRegisterSuccess: 注册成功");
                }

                @Override
                public void OnRegisterFailed() {
                    Log.d(TAG, "OnRegisterFailed: 注册失败"+ShareUtils.getUserInfo());
                }
            });
        }else{
            switch (code){
                case 1:
                    Toast.makeText(this,"请填写用户名",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this,"请填写确认密码",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this,"两次填写的密码不一致",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private int enableRegister() {
        username = etname.getText().toString();
        password1 = etpass1.getText().toString();
        password2 = etpass2.getText().toString();
        if(username == null||username.equals("")||username.length() == 0){
            return 1;
        }else if(password1 == null||password1.equals("")||password1.length() == 0){
            return 2;
        }else if(password2 == null || password2.equals("") || password2.length() == 0){
            return 3;
        }else if(!password1.equals(password2)){
            return 4;
        }
        return 0;
    }
}

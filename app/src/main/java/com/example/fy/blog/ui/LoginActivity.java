package com.example.fy.blog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fy.blog.R;
import com.example.fy.blog.bean.LoginEntity;
import com.example.fy.blog.interfaces.OnLoginCallback;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.util.Contasts;
import com.example.fy.blog.util.ShareUtils;

/**
 * Created by fy on 2016/3/17.
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    EditText etName,etPass;
    Button bnLogin,bnRegister;
    String userName,passWord;
    android.support.v7.app.ActionBar actionBar;
    String url = "";
    private ProgressBar waitProgress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();
    }
    private void initViews() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        etName = (EditText)findViewById(R.id.username);
        etPass = (EditText)findViewById(R.id.password);

        etName.setText(ShareUtils.getUsername());
        etPass.setText(ShareUtils.getPassword());

        bnLogin = (Button)findViewById(R.id.login);
        bnRegister = (Button)findViewById(R.id.register);
        waitProgress = (ProgressBar) findViewById(R.id.wait_progress);
        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
        bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 判断是否可以进行登录
      * @return 1:用户名不符合 2：密码不符合
     */
    private int enableLogin() {
        userName = etName.getText().toString();
        passWord = etPass.getText().toString();

        if(userName == null || userName.equals("") || userName.length() == 0) {
            return Contasts.RESULT_USERNAME_ERROR;
        }else if(passWord == null || passWord.equals("") || passWord.length() == 0) {
            return Contasts.RESULT_PASSWORD_ERROR;
        }
        return 0;
    }

    private void handleLogin() {
        int code = enableLogin();
        waitProgress.setVisibility(View.VISIBLE);
        if (code == 0) {
            //加密算法：
            Integer pass = new Integer(passWord);
            int p,q,e,d,m,n,t,c,r;
            p = 23;
            q = 37;
            char s;
            n = p*q;
            e = 47;
            t=(p-1)*(q-1);
            d=1;
            while(((e*d)%t)!=1)
                d++;






            APIUtils.login(userName, passWord, new OnLoginCallback() {
                @Override
                public void onLoginSuccess(LoginEntity entity) {
                    waitProgress.setVisibility(View.INVISIBLE);
                    //AppContext.getIntance().saveLoginInfo(entity.getUser());
                    //向share中写入一些信息
                    ShareUtils.putUserInfo(userName,passWord);
                    ShareUtils.putLoginState(true);
                    ShareUtils.putLoginInfo(entity.getUser());
                    sendBroadcast(new Intent("com.example.blog.LOGIN"));
                    finish();
                    Log.d(TAG, "onLoginSuccess: 登录成功");
                }

                @Override
                public void onLoginFailed(int type, String msg) {
                    waitProgress.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "onLoginFailed: 登录失败");
                }
            });
        }else {
            waitProgress.setVisibility(View.INVISIBLE);
            if (code == Contasts.RESULT_USERNAME_ERROR) {
                Toast.makeText(this,"用户名长度不符",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"密码长度不符",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

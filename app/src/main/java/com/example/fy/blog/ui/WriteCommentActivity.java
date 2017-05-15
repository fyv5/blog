package com.example.fy.blog.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fy.blog.R;
import com.example.fy.blog.bean.Comment;
import com.example.fy.blog.bean.User;
import com.example.fy.blog.interfaces.OnSendCommentCallBack;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.util.LogUtil;
import com.example.fy.blog.util.ShareUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fy on 2016/6/7.
 */
public class WriteCommentActivity extends Activity{
    public Toolbar toolbar;
    public TextView ok;
    public TextView cancel;
    public EditText tv_comment;
    public String blogId;
    public String commentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wriite_comment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        blogId = bundle.getString("blogId");
        commentId = new Integer(bundle.getInt("commentId")+1).toString();
        LogUtil.d("WriteComment",blogId);
        LogUtil.d("WriteComment",commentId);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tv_comment = (EditText)findViewById(R.id.write_comment);
        ok = (TextView)findViewById(R.id.ok);
        cancel = (TextView)findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //点击确定之后的数据处理
    private void sendComment() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        User user = ShareUtils.getLoginInfo();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        Comment comment = new Comment(commentId,null,blogId, user.get_username(),
                user.get_portrait(),time,tv_comment.getText().toString());
        bundle.putSerializable("Comment",comment);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        APIUtils.sendComment(blogId, new OnSendCommentCallBack() {
            @Override
            public void OnSendCommentSuccess() {
                Toast.makeText(WriteCommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnSendCommentFailed() {
                Toast.makeText(WriteCommentActivity.this,"评论失败",Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }
}

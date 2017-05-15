package com.example.fy.blog.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fy.blog.R;

/**
 * Created by fy on 2016/4/5.
 */
public class TipInfoLayout extends FrameLayout {

    private String netWorkError = "轻触重新加载";
    private String empty = "暂无数据";

    private ProgressBar mPbProgressBar;
    private View mTipContainer;
    private TextView mTvTipState;
    private TextView mTvTipMsg;


    public TipInfoLayout(Context context) {
        super(context);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tip_info_layout,null);
        mPbProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        mTvTipState = (TextView) view.findViewById(R.id.tv_tip_state);
        mTvTipMsg = (TextView) view.findViewById(R.id.tv_tip_msg);
        mTipContainer = view.findViewById(R.id.ll_tip);
        setLoading();
        addView(view);
    }

    public  void setLoading() {
        this.setVisibility(VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTipContainer.setVisibility(View.GONE);
    }
    public void setHiden(){
        this.setVisibility(View.GONE);
    }

}

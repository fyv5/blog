package com.example.fy.blog.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.adapter.CommentAdapter;
import com.example.fy.blog.adapter.CommonAdapter;
import com.example.fy.blog.bean.Entity;
import com.example.fy.blog.bean.MessageData;
import com.example.fy.blog.interfaces.OnBlogCallback;
import com.example.fy.blog.ui.basefragment.BaseFragment;
import com.example.fy.blog.util.LogUtil;
import com.example.fy.blog.widget.TipInfoLayout;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;
import java.util.Map;

/**
 * Created by fy on 2016/4/4.
 * 下拉刷新界面的基类
 */
public abstract class BaseSwipeRefreshFragment<T extends Entity> extends BaseFragment
implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,AbsListView.OnScrollListener {
    //没有状态

    public static final int LISTVIEW_ACTION_NONE = -1;
    //初始化时，加载缓存状态
    public static final int LISTVIEW_ACTION_INIT = 1;
    //刷新状态，显示toast
    public static final int LISTVIEW_ACTION_REFRESH = 2;
    //下拉到底部时，获取下一页的状态
    public static final int LISTVIEW_ACTION_SCROLL = 3;

    static final int STATE_NONE = -1;
    static final int STATE_LOADING = 0;
    static final int STATE_LOADED = 1;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ListView mListView;
    private View mFooterView;
    private CommonAdapter<T> mAdapter;

    private View mFooterProgressBar;
    private TextView mFooterTextView;
    protected TipInfoLayout mTipInfo;

    //加载当前状态
    private int mState = STATE_NONE;
    //UI状态
    private int mListViewAction  = LISTVIEW_ACTION_NONE;
    //当前数据状态，如果已经全部加载，就不执行滚动到底就加载的情况
    private int dataState = LISTVIEW_ACTION_NONE;

    protected int mcurrentPage = 1;


    protected OnBlogCallback mHandler = new OnBlogCallback() {
        private List<T> datas = null;
        @Override
        public void OnGetBlogSuccess(String response) {
            datas = getDatas(response);
            mTipInfo.setHiden();
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            loadDataSuccess(datas);
            setSwipeRefreshLoadedState();
        }

        @Override
        public void OnGetBlogFailed() {
            LogUtil.d("BaseSwipRefreshFragment","获取博客失败");
        }
    };

    protected void loadDataSuccess(List<T> datas){
        if(datas == null) return;
        //如果数据小于20页那么直接显示数据已经加载完毕显示到底部了
        if(datas.size()<20){
            dataState = MessageData.MESSAGE_STATE_FULL;
            setFooterFullState();
        }
        if(mcurrentPage == 1){
            mAdapter.clear();
        }
        mAdapter.addItem(datas);
        //整个列表为空
        if(mAdapter.getCount() == 0){
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mTipInfo.setVisibility(View.VISIBLE);
            //设置mTipInfo为暂无数据
            LogUtil.d("BaseSwipeRefreshFragment","暂时没有数据");
        }
    }

    //将数据从json字符串中解析出来
    protected abstract List getDatas(String response);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFooterView = inflater.inflate(R.layout.listview_footer,null);
        mFooterProgressBar = mFooterView.findViewById(R.id.listview_foot_progress);
        mFooterTextView = (TextView)mFooterView.findViewById(R.id.listview_foot_more);
        return inflater.inflate(R.layout.base_swiperefresh,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        setupListView();
        if(mListViewAction == LISTVIEW_ACTION_REFRESH){
            setSwipeRefreshLoadingState();
        }
    }

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }
    private void onVisible() {
        lazyLoad();
    }

    private void onInvisible() {

    }
    protected abstract void lazyLoad();

    private void initView(View view){
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefreshlayout);
        mListView = (ListView)view.findViewById(R.id.listview);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.swiperefresh_color1,
                R.color.swiperefresh_color2,R.color.swiperefresh_color3,
                R.color.swiperefresh_color4);
        mTipInfo = (TipInfoLayout)view.findViewById(R.id.tip_info);
        mTipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }
    /**
     * 初始化listView
     */
    private void setupListView() {
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(this);
        mListView.addFooterView(mFooterView);
        mListView.setAdapter(mAdapter);
//        mListView.setOnScrollListener(new PauseOnScrollListener(mAdapter.imageLoader,true,true));
    }

    @Override
    public void onRefresh() {
        mcurrentPage = 1;
        requestData();
        LogUtil.d("BaseSwipeRefreshFragment","刷新成功");
    }

    /**
     * 设置顶部正在加载的状态
     */
     void setSwipeRefreshLoadingState() {
        if(mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setRefreshing(true);
            //防止多次刷新。刷新一次就设置为不能刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    void setSwipeRefreshLoadedState(){
        if(mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setRefreshing(false);
            //设置可以进行刷新
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     * 设置底部已经全部加载的状态
     */
    void setFooterFullState(){
        if(mFooterView != null){
            mFooterProgressBar.setVisibility(View.GONE);
            mFooterTextView.setText(R.string.load_full);
        }
    }

    /**
     * 设置底部加载中的状态
     */
    void setFooterLoadingState(){
        if(mFooterView != null){
            mFooterProgressBar.setVisibility(View.VISIBLE);
            mFooterTextView.setText(R.string.load_ing);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        //点击了底部
        if(view == mFooterView){
            return;
        }
        T data = mAdapter.getItem(position);
        if(data == null) return;
        onItemClick(position,data);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //如果获取的mAdapter为空或者项目数量为0就直接返回
        if(mAdapter == null || mAdapter.getCount() == 0){
            return;
        }
        //数据已经全部加载，或数据为空，或正在加载，不处理滚动事件
        if(dataState == MessageData.MESSAGE_STATE_FULL
                ||dataState == MessageData.MESSAGE_STATE_EMPTY
                ||mState == STATE_LOADING){
            return;
        }
        //判断是否滚动到底部
        boolean scrollEnd = false;
        try{
            if(view.getPositionForView(mFooterView) == view.getLastVisiblePosition())
                scrollEnd = true;
        }catch (Exception e){
            scrollEnd = false;
        }
        if(scrollEnd){
            ++mcurrentPage;
            requestData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }
    protected abstract void onItemClick(int position, T data);

    protected abstract void requestData();

    public abstract CommonAdapter<T> getAdapter();

    protected String getEmptyTip(){
        return "暂无数据";
    }
}

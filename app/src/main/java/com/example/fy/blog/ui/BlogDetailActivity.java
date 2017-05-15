package com.example.fy.blog.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fy.blog.R;
import com.example.fy.blog.adapter.CommentAdapter;
import com.example.fy.blog.bean.Blog;
import com.example.fy.blog.bean.Comment;
import com.example.fy.blog.interfaces.OnGetCommentCallback;
import com.example.fy.blog.ui.fragments.BlogDetailHeaderFragment;
import com.example.fy.blog.util.APIUtils;
import com.example.fy.blog.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fy on 2016/6/6.
 */
public class BlogDetailActivity extends BaseActivity {

    public ListView listView;

    public BlogDetailHeaderFragment mHeadFragment;

    public ActionBar actionBar;

    public TextView tv_zan,tv_cai;

    public LinearLayout zanGroup,caiGroup;

    public Blog blog;

    public List<Comment> mDatas = new ArrayList<>();

    public Button b_writecomment;

    public CommentAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        blog = (Blog)bundle.getSerializable("Blog");
        LogUtil.d("BlogDetailAcitivity111",blog.getBlogId());
        initView(BlogDetailActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Comment comment = (Comment)bundle.getSerializable("Comment");
            mDatas.add(0,comment);
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(Context contxt) {
        actionBar = getSupportActionBar();
        actionBar.setTitle("博客详情");
        mHeadFragment = new BlogDetailHeaderFragment();
        listView = (ListView)findViewById(R.id.blog_detail);
        final View mHeaderView = getLayoutInflater().inflate(R.layout.blog_detail_header,null);
        //加载list数据
        APIUtils.getComment(blog.getBlogId(), new OnGetCommentCallback() {
            @Override
            public void OnGetCommentSuccess(String response) {
                LoadDataSuccess(parseResponse(response));
                LogUtil.d("BlogDetailActivity",response);
            }

            @Override
            public void OnGetCommentFailed() {
                LogUtil.d("BlogDetailActivity","加载评论失败");
            }
        });
        adapter = new CommentAdapter(contxt,mDatas);
        listView.addHeaderView(mHeaderView);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new PauseOnScrollListener(CommentAdapter.imageLoader,true,true));
        mHeaderView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                LogUtil.d("博客细节","fragment被添加到activity");
                getFragmentManager().beginTransaction()
                        .replace(R.id.blog_detail_container,mHeadFragment).commit();
                Bundle arguments = new Bundle();
                arguments.putSerializable("Blog",blog);
                mHeadFragment.setArguments(arguments);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
        tv_zan = (TextView)findViewById(R.id.zan);
        tv_cai = (TextView)findViewById(R.id.cai);
        tv_zan.setText(new Integer(blog.getZan()).toString());
        tv_cai.setText(new Integer(blog.getCai()).toString());
        zanGroup = (LinearLayout)findViewById(R.id.zangroup);
        caiGroup = (LinearLayout)findViewById(R.id.caigroup);
        actionBar.setDisplayHomeAsUpEnabled(true);
        b_writecomment = (Button)findViewById(R.id.comment);
        zanGroup.setOnClickListener(new View.OnClickListener() {
            boolean isFirst = true;
            @Override
            public void onClick(View v) {
                LogUtil.d("BlogDetailActivity","点了一下赞");

            }
        });
        caiGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("BlogDetailActivity","点了一下踩");
            }
        });
        b_writecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BlogDetailActivity.this,WriteCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("commentId",Integer.parseInt(mDatas.get(0).getId()));
                bundle.putString("blogId",blog.getBlogId());
                intent.putExtras(bundle);
                LogUtil.d("BlogDetailActivity",mDatas.get(0).getId());
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });
    }

    private void LoadDataSuccess(List<Comment> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    /**
     * 将评论json数据解析为comment的list
     * @param response
     */
    private List<Comment> parseResponse(String response) {
        Gson gson = new Gson();
        List<Comment> datas = new ArrayList<>();
        try{
            datas = gson.fromJson(response, new TypeToken<List<Comment>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return datas;
    }

    //设置后退键按钮
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

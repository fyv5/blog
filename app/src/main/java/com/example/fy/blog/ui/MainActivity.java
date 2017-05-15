package com.example.fy.blog.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.fy.blog.R;
import com.example.fy.blog.interfaces.DrawerMenuCallBack;
import com.example.fy.blog.ui.fragments.ExploreListViewFragment;
import com.example.fy.blog.ui.fragments.MySelfViewPagerFragment;


public class MainActivity extends AppCompatActivity implements DrawerMenuCallBack{

    final String DRAWER_MENU_TAG = "drawer_menu";
    final String CONTENT_TAG_EXPLORE = "content_explore";
    final String CONTENT_TAG_MYSELF = "content_myself";
    final String[] CONTENT = {
      CONTENT_TAG_EXPLORE, CONTENT_TAG_MYSELF
    };
    final String FRAGMENT[] = {
            ExploreListViewFragment.class.getName(),
            MySelfViewPagerFragment.class.getName()

    };
    final String TITLES[] = {
            "发现",
            "我的"
    };
    //
    private String mCurrentContentTag;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle, mTitle;
    private ActionBar actionBar;
    private DrawerNavigationMenu mMenu = new DrawerNavigationMenu();
    private Fragment currentSupportFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerListener(new DrawerMenuListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, 0, 0);
        if(null == savedInstanceState){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_slidingmenu_frame,mMenu,DRAWER_MENU_TAG).commit();


//            changeFragment(R.id.main_content, new MySelfViewPagerFragment());
            changeFragment(R.id.main_content,new ExploreListViewFragment());

            mTitle = "发现";
            actionBar.setTitle(mTitle);
            mCurrentContentTag = CONTENT_TAG_EXPLORE;
        }
    }

    /**
     * 用fragment替换视图
     * @param resView 将要被替换的视图
     * @param targetFragment 用来替换的视图
     */
    public void changeFragment(int resView,Fragment targetFragment){
        if(targetFragment.equals(currentSupportFragment)){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.main_content,targetFragment);
        if(!targetFragment.isAdded()){
            transaction.add(resView,targetFragment,targetFragment.getClass().getName());
        }
        if(targetFragment.isHidden()){
            transaction.show(targetFragment);
        }
        if(currentSupportFragment != null && currentSupportFragment.isVisible()){
            transaction.hide(currentSupportFragment);
            Log.d("tag","currentSupportFragment--------");
        }
        currentSupportFragment = targetFragment;
        Log.d("TAG4",currentSupportFragment.toString()+currentSupportFragment.isVisible()+targetFragment.isHidden());

        transaction.commit();
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
    }
    /** 菜单键点击的事件处理 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /** 设备配置改变时 */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClickLogin() {
        showLoginActivity();

    }

    @Override
    public void onClickSetting() {
        showSettingActivity();
    }

    @Override
    public void onClickMyself() {
        showMainContent(1);
    }

    @Override
    public void onClickExplore() {
        showMainContent(0);
    }


    private class DrawerMenuListener implements DrawerLayout.DrawerListener{

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView,slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }
    private void showLoginActivity(){
        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
    }

    private void showSettingActivity(){
        Intent intent = new Intent(getApplication(),SettingActivity.class);
        startActivity(intent);
    }
    private void showMainContent(int pos){
        mDrawerLayout.closeDrawers();
        String tag = CONTENT[pos];
        if(tag.equalsIgnoreCase(mCurrentContentTag)) return;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content,Fragment.instantiate(this,FRAGMENT[pos]))
                .commit();
        actionBar.setTitle(TITLES[pos]);
        mTitle = TITLES[pos];
        mCurrentContentTag = tag;
    }
}

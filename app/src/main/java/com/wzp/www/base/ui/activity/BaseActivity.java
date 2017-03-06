package com.wzp.www.base.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wzp.www.base.R;
import com.wzp.www.base.helper.ActivityHelper;

/**
 * Activity基类
 *
 * @author wzp
 * @since 2017-03-03
 */
public abstract class BaseActivity extends AppCompatActivity {
    private AppBarLayout mAblNavTop;
    private Toolbar mTbNavTop;
    private ActionBar mActionBar;
    private INavTopBackListener mNavTopBackListener;
    private int mNavTopMenu;

    public interface INavTopBackListener {
        void onBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindContentView());
        initNavTop();
        initContentViewItem();
        loadData();
        bindContentViewItem(savedInstanceState);
        ActivityHelper.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.remove(this);
    }

    /**
     * 初始化顶部导航栏
     */
    protected void initNavTop() {
        mAblNavTop = (AppBarLayout) findViewById(R.id.abl_nav_top);
        mTbNavTop = (Toolbar) findViewById(R.id.tb_nav_top);
        if (mTbNavTop != null) {
            setSupportActionBar(mTbNavTop);
        }
        mActionBar = getSupportActionBar();
    }

    /**
     * 隐藏顶部导航栏
     */
    protected void hideNavTop() {
        if (mAblNavTop != null) {
            mAblNavTop.setVisibility(View.GONE);
        }
    }

    /**
     * 显示顶部导航栏
     */
    protected void showNavTop() {
        if (mAblNavTop != null) {
            mAblNavTop.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置顶部导航栏返回键
     *
     * @param resId    自定义返回键资源ID
     * @param listener 自定义返回键操作
     */
    protected void setNavTopBack(int resId, INavTopBackListener listener) {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            if (resId != 0) {
                mActionBar.setHomeAsUpIndicator(resId);
            }
            if (listener != null) {
                mNavTopBackListener = listener;
            }
        }
    }

    /**
     * 设置顶部导航栏返回键
     */
    protected void setNavTopBack() {
        setNavTopBack(0, null);
    }

    /**
     * 设置顶部导航栏返回键
     *
     * @param listener 自定义返回键操作
     */
    protected void setNavTopBack(INavTopBackListener listener) {
        setNavTopBack(0, listener);
    }

    /**
     * 设置顶部导航栏标题
     */
    protected void setNavTopTitle(int resId) {
        if (mActionBar != null && resId != 0) {
            mActionBar.setTitle(resId);
        }
    }

    /**
     * 设置顶部导航栏标题
     */
    protected void setNavTopTitle(String title) {
        if (mActionBar != null && !TextUtils.isEmpty(title)) {
            mActionBar.setTitle(title);
        }
    }

    /**
     * 绑定顶部导航栏菜单
     */
    protected void bindNavTopMenu(int resId) {
        if (resId != 0) {
            mNavTopMenu = resId;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavTopMenu != 0) {
            getMenuInflater().inflate(mNavTopMenu, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mNavTopBackListener != null) {
                    mNavTopBackListener.onBack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    /**
     * 获取根布局
     */
    protected ViewGroup getRootView() {
        return (ViewGroup) getWindow().getDecorView();
    }

    /**
     * 绑定活动对应布局ID
     */
    protected abstract int bindContentView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化布局元素
     */
    protected abstract void initContentViewItem();

    /**
     * 绑定布局元素
     */
    protected abstract void bindContentViewItem(Bundle savedInstanceState);

}

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
    private AppBarLayout mAblNav;
    private Toolbar mTbNav;
    private ActionBar mActionBar;
    private INavBackListener mNavBackListener;
    private int mNavMenu;

    public interface INavBackListener {
        void onBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initNav();
        initContentView();
        loadData();
        bindContentView(savedInstanceState);
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
    protected void initNav() {
        mAblNav = (AppBarLayout) findViewById(R.id.abl_generic_nav);
        mTbNav = (Toolbar) findViewById(R.id.tb_generic_nav);
        if (mTbNav != null) {
            setSupportActionBar(mTbNav);
        }
        mActionBar = getSupportActionBar();
    }

    /**
     * 隐藏顶部导航栏
     */
    protected void hideNav() {
        if (mAblNav != null) {
            mAblNav.setVisibility(View.GONE);
        }
    }

    /**
     * 显示顶部导航栏
     */
    protected void showNav() {
        if (mAblNav != null) {
            mAblNav.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置顶部导航栏返回键
     *
     * @param resId    自定义返回键资源ID
     * @param listener 自定义返回键操作
     */
    protected void setNavBack(int resId, INavBackListener listener) {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            if (resId != 0) {
                mActionBar.setHomeAsUpIndicator(resId);
            }
            if (listener != null) {
                mNavBackListener = listener;
            }
        }
    }

    /**
     * 设置顶部导航栏返回键
     */
    protected void setNavBack() {
        setNavBack(0, null);
    }

    /**
     * 设置顶部导航栏返回键
     *
     * @param listener 自定义返回键操作
     */
    protected void setNavBack(INavBackListener listener) {
        setNavBack(0, listener);
    }

    /**
     * 设置顶部导航栏标题
     */
    protected void setNavTitle(int resId) {
        if (mActionBar != null && resId != 0) {
            mActionBar.setTitle(resId);
        }
    }

    /**
     * 设置顶部导航栏标题
     */
    protected void setNavTitle(String title) {
        if (mActionBar != null && !TextUtils.isEmpty(title)) {
            mActionBar.setTitle(title);
        }
    }

    /**
     * 绑定顶部导航栏菜单
     */
    protected void bindNavMenu(int resId) {
        if (resId != 0) {
            mNavMenu = resId;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavMenu != 0) {
            getMenuInflater().inflate(mNavMenu, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mNavBackListener != null) {
                    mNavBackListener.onBack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    /**
     * 获取活动对应布局ID
     */
    protected abstract int getContentView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化布局元素
     */
    protected abstract void initContentView();

    /**
     * 绑定布局元素
     */
    protected abstract void bindContentView(Bundle savedInstanceState);

}

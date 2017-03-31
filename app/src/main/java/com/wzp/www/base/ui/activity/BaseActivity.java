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

import com.wzp.www.base.R;
import com.wzp.www.base.bean.NavMenu;
import com.wzp.www.base.helper.ActivityHelper;
import com.wzp.www.base.helper.L;
import com.wzp.www.base.helper.ResHelper;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Activity基类
 *
 * @author wzp
 * @since 2017-03-03
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private AppBarLayout mAblNav;
    private Toolbar mTbNav;
    private ActionBar mActionBar;
    private INavBackListener mNavBackListener;
    private int mNavMenuResId;
    private String mNavMenuJsonStr;

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
     * 从JSON字符串加载顶部导航栏菜单，优先级高于从资源文件加载顶部导航栏菜单，json格式如下
     * <pre>
     * [
     *     {
     *         "groupId": 0,
     *         "itemId": 0,
     *         "orderId": 0,
     *         "title": "",
     *         "icon": "drawable资源名"
     *     }
     * ]
     * </pre>
     */
    protected void loadNavMenu(String navMenuJsonStr) {
        mNavMenuJsonStr = navMenuJsonStr;
    }

    /**
     * 从资源文件加载顶部导航栏菜单
     */
    protected void loadNavMenu(int navMenuResId) {
        mNavMenuResId = navMenuResId;
    }

    /**
     * 反射方式解决Android4.0中menu中android:icon不起作用
     */
    private void setMenuIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method method = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean
                    .class);
            method.setAccessible(true);
            method.invoke(menu, enable);
        } catch (Exception e) {
            L.e(TAG, e.getMessage());
        }
    }

    /**
     * @return true(显示菜单)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        setMenuIconEnable(menu, true);
        if (!TextUtils.isEmpty(mNavMenuJsonStr)) {
            List<NavMenu> navMenus = NavMenu.getNavMenus(mNavMenuJsonStr);
            for (NavMenu navMenu : navMenus) {
                MenuItem menuItem = menu.add(navMenu.mGroupId, navMenu.mItemId, navMenu
                        .mOrderId, navMenu.mTitle);
                if (!TextUtils.isEmpty(navMenu.mIcon)) {
                    menuItem.setIcon(ResHelper.getDrawable(navMenu.mIcon));
                }
            }
        } else if (mNavMenuResId != 0) {
            getMenuInflater().inflate(mNavMenuResId, menu);
        }
        return true;
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

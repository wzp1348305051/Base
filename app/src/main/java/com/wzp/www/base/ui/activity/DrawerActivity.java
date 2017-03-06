package com.wzp.www.base.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.wzp.www.base.R;

/**
 * 侧边导航菜单活动
 */
public class DrawerActivity extends BaseFragmentActivity {
    protected DrawerLayout mDlMain;
    protected NavigationView mNvMenu;

    @Override
    protected int bindContentView() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initNavTop() {
        super.initNavTop();
        setNavTopBack(R.drawable.activity_drawer_nav_menu, new INavTopBackListener() {
            @Override
            public void onBack() {
                mDlMain.openDrawer(GravityCompat.START);
            }
        });
        setNavTopTitle(R.string.activity_drawer_nav_home);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initContentViewItem() {
        mDlMain = (DrawerLayout) findViewById(R.id.dl_drawer);
        mNvMenu = (NavigationView) findViewById(R.id.nv_drawer_nav_menu);
    }

    @Override
    protected void bindContentViewItem(Bundle savedInstanceState) {
        mNvMenu.setCheckedItem(R.id.activity_drawer_nav_home);
        mNvMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        break;
                }
                mDlMain.closeDrawers();
                return true;
            }
        });
    }
}

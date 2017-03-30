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
    protected DrawerLayout mDlRoot;
    protected NavigationView mNvMenu;

    @Override
    protected int getContentView() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initNav() {
        super.initNav();
        setNavBack(R.drawable.ic_activity_drawer_menu, new INavBackListener() {
            @Override
            public void onBack() {
                mDlRoot.openDrawer(GravityCompat.START);
            }
        });
        setNavTitle(R.string.action_activity_drawer_menu_home);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initContentView() {
        mDlRoot = (DrawerLayout) findViewById(R.id.dl_activity_drawer_root);
        mNvMenu = (NavigationView) findViewById(R.id.nv_activity_drawer_menu);
    }

    @Override
    protected void bindContentView(Bundle savedInstanceState) {
        mNvMenu.setCheckedItem(R.id.activity_drawer_menu_home);
        mNvMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        break;
                }
                mDlRoot.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Bundle data) {

    }
}

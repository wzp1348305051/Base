package com.wzp.www.base.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wzp.www.base.ui.fragment.IFragmentInteractionListener;

/**
 * FragmentActivity基类
 *
 * @author wzp
 * @since 2017-03-03
 */
public abstract class BaseFragmentActivity extends BaseActivity implements IFragmentInteractionListener {
    protected Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 切换Fragment
     */
    protected void switchFragment(int containerId, Fragment fragment) {
        if (fragment != null) {
            if (mCurrentFragment == null) {
                getSupportFragmentManager().beginTransaction().add(containerId,
                        fragment).commit();
            } else {
                if (fragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide
                            (mCurrentFragment).show(fragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().hide
                            (mCurrentFragment).add(containerId, fragment).commit();
                }
            }
            mCurrentFragment = fragment;
        }
    }

}

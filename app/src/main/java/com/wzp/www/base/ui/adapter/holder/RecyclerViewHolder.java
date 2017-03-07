package com.wzp.www.base.ui.adapter.holder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzp.www.base.helper.GlideHelper;
import com.wzp.www.base.helper.L;

import java.io.File;

/**
 * Created by wengzhipeng on 17/3/6.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = RecyclerViewHolder.class.getSimpleName();
    /**
     * 缓存的控件
     */
    private SparseArray<View> mViews;
    private View mRootView;
    private Activity mActivity;
    private Fragment mFragment;

    private RecyclerViewHolder(View view, Activity activity, Fragment fragment) {
        super(view);
        mViews = new SparseArray<>();
        mRootView = view;
        mActivity = activity;
        mFragment = fragment;
    }

    public static RecyclerViewHolder getViewHolder(View view, Activity activity, Fragment
            fragment) {
        return new RecyclerViewHolder(view, activity, fragment);
    }

    /**
     * 获取布局子控件
     *
     * @param viewId 控件id
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 加载TextView文本内容
     *
     * @param viewId TextView控件id
     * @param text   文本内容
     */
    public void setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
    }

    /**
     * 加载ImageView图片
     *
     * @param viewId ImageView控件id
     * @param resId  图片资源id
     */
    public void setImage(int viewId, int resId) {
        ImageView view = getView(viewId);
        if (mFragment != null) {
            GlideHelper.configDefault(Glide.with(mFragment).load(resId)).into(view);
        } else if (mActivity != null) {
            GlideHelper.configDefault(Glide.with(mActivity).load(resId)).into(view);
        } else {
            L.e(TAG, "Activity or fragment is null");
        }
    }

    /**
     * 加载ImageView图片
     *
     * @param viewId ImageView控件id
     * @param uri    图片URL或文件路径
     */
    public void setImage(int viewId, String uri) {
        ImageView view = getView(viewId);
        if (mFragment != null) {
            GlideHelper.configDefault(Glide.with(mFragment).load(uri)).into(view);
        } else if (mActivity != null) {
            GlideHelper.configDefault(Glide.with(mActivity).load(uri)).into(view);
        } else {
            L.e(TAG, "Activity or fragment is null");
        }
    }

    /**
     * 加载ImageView图片
     *
     * @param viewId ImageView控件id
     * @param bytes  图片字节数组
     */
    public void setImage(int viewId, byte[] bytes) {
        ImageView view = getView(viewId);
        if (mFragment != null) {
            GlideHelper.configDefault(Glide.with(mFragment).load(bytes)).into(view);
        } else if (mActivity != null) {
            GlideHelper.configDefault(Glide.with(mActivity).load(bytes)).into(view);
        } else {
            L.e(TAG, "Activity or fragment is null");
        }
    }

    /**
     * 加载ImageView图片
     *
     * @param viewId ImageView控件id
     * @param file   图片文件句柄
     */
    public void setImage(int viewId, File file) {
        ImageView view = getView(viewId);
        if (mFragment != null) {
            GlideHelper.configDefault(Glide.with(mFragment).load(file)).into(view);
        } else if (mActivity != null) {
            GlideHelper.configDefault(Glide.with(mActivity).load(file)).into(view);
        } else {
            L.e(TAG, "Activity or fragment is null");
        }
    }

}

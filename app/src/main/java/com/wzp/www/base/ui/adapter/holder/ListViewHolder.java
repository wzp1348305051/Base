package com.wzp.www.base.ui.adapter.holder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzp.www.base.helper.GlideHelper;
import com.wzp.www.base.helper.L;
import com.wzp.www.base.helper.SystemServiceHelper;

import java.io.File;

/**
 * 控件容器，用于ListViewAdapter
 */
public class ListViewHolder {
    private static final String TAG = ListViewHolder.class.getSimpleName();
    /**
     * 缓存的控件
     */
    private SparseArray<View> mViews;
    /**
     * ViewHolder绑定布局项位置
     */
    private Activity mActivity;
    private Fragment mFragment;
    /**
     * ViewHolder绑定布局项位置
     */
    private int mPosition;
    /**
     * ViewHolder绑定布局,因为ViewHolder设置在绑定布局tag中,便于重用
     */
    private View mRootView;

    private ListViewHolder(Activity activity, Fragment fragment, int position,
                           ViewGroup parent, int layoutId) {
        mViews = new SparseArray<>();
        mActivity = activity;
        mFragment = fragment;
        mPosition = position;
        mRootView = SystemServiceHelper.getLayoutInflater(mActivity).inflate(layoutId,
                parent, false);
        mRootView.setTag(this);
    }

    public static ListViewHolder getViewHolder(Activity activity, Fragment fragment,
                                               int position, View convertView,
                                               ViewGroup parent, int layoutId) {
        ListViewHolder holder;
        if (convertView == null) {
            holder = new ListViewHolder(activity, fragment, position, parent, layoutId);
        } else {
            holder = (ListViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        return holder;
    }

    /**
     * 获取ViewHolder绑定布局
     */
    public View getRootView() {
        return mRootView;
    }

    /**
     * 获取ViewHolder绑定布局项位置
     */
    public int getPosition() {
        return mPosition;
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

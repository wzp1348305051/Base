package com.wzp.www.base.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wzp.www.base.helper.SystemServiceHelper;
import com.wzp.www.base.ui.adapter.holder.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wengzhipeng on 17/3/6.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected Activity mActivity;
    protected Fragment mFragment;
    protected int mItemLayoutId;
    protected List<T> mData;

    public RecyclerViewAdapter(Activity activity, int itemLayoutId) {
        this(activity, null, itemLayoutId, new ArrayList<T>());
    }

    public RecyclerViewAdapter(Activity activity, int itemLayoutId, List<T> data) {
        this(activity, null, itemLayoutId, data);
    }

    public RecyclerViewAdapter(Activity activity, Fragment fragment, int itemLayoutId,
                               List<T> data) {
        mActivity = activity;
        mFragment = fragment;
        mItemLayoutId = itemLayoutId;
        mData = data;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 设置列表数据,会清空原有数据
     *
     * @param data 插入数据集合
     */
    public void setData(Collection<T> data) {
        mData.clear();
        addAll(data);
    }

    /**
     * 设置列表数据,会清空原有数据,并刷新列表
     *
     * @param data 插入数据集合
     */
    public void setDataNotify(Collection<T> data) {
        mData.clear();
        addAllNotify(data);
    }

    /**
     * 在列表尾部批量插入数据
     *
     * @param data 插入数据集合
     */
    public void addAll(Collection<T> data) {
        mData.addAll(data);
    }

    /**
     * 在列表尾部批量插入数据,并刷新列表
     *
     * @param data 插入数据集合
     */
    public void addAllNotify(Collection<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置批量插入数据
     *
     * @param data     插入数据集合
     * @param position 插入位置
     */
    public void addAll(Collection<T> data, int position) {
        mData.addAll(position, data);
    }

    /**
     * 在指定位置批量插入数据,并刷新列表
     *
     * @param data     插入数据集合
     * @param position 插入位置
     */
    public void addAllNotify(Collection<T> data, int position) {
        mData.addAll(position, data);
        notifyDataSetChanged();
    }

    /**
     * 在列表尾部插入数据
     *
     * @param datum 插入数据
     */
    public void add(T datum) {
        mData.add(datum);
    }

    /**
     * 在列表尾部插入数据,并刷新列表
     *
     * @param datum 插入数据
     */
    public void addNotify(T datum) {
        mData.add(datum);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置插入数据
     *
     * @param datum    插入数据
     * @param position 插入位置
     */
    public void add(T datum, int position) {
        mData.add(position, datum);
    }

    /**
     * 在指定位置插入数据,并刷新列表
     *
     * @param datum    插入数据
     * @param position 插入位置
     */
    public void addNotify(T datum, int position) {
        mData.add(position, datum);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置替换数据
     *
     * @param datum    替换数据
     * @param position 替换位置
     */
    public void replace(T datum, int position) {
        mData.remove(position);
        mData.add(position, datum);
    }

    /**
     * 在指定位置替换数据,并刷新列表
     *
     * @param datum    替换数据
     * @param position 替换位置
     */
    public void replaceNotify(T datum, int position) {
        mData.remove(position);
        mData.add(position, datum);
        notifyDataSetChanged();
    }

    /**
     * 清除列表数据
     */
    public void clear() {
        mData.clear();
    }

    /**
     * 清除列表数据,并刷新列表
     */
    public void clearNotify() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定位置列表数据
     *
     * @param position 指定位置
     */
    public void remove(int position) {
        mData.remove(position);
    }

    /**
     * 删除指定位置列表数据,并刷新列表
     *
     * @param position 指定位置
     */
    public void removeNotify(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 创建循环构建器中的ViewHolder
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecyclerViewHolder.getViewHolder(SystemServiceHelper.getLayoutInflater
                (mActivity).inflate(mItemLayoutId, parent, false), mActivity, mFragment);
    }

    /**
     * 填充ViewHolder中数据
     */
    @Override
    public abstract void onBindViewHolder(RecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}

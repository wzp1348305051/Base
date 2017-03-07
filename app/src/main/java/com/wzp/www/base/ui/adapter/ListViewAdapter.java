package com.wzp.www.base.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wzp.www.base.ui.adapter.holder.ListViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 列表通用适配器
 * <p>单布局列表需重写onBindViewHolder(holder, position)</p>
 * <p>多布局列表需重写onBindViewHolder(holder, position), getViewTypeCount(), getItemViewType
 * (position), getItemLayoutIdByItemViewType(type)</p>
 */
public abstract class ListViewAdapter<T> extends BaseAdapter {
    protected Activity mActivity;
    protected Fragment mFragment;
    protected int mItemLayoutId;
    protected List<T> mData;

    public ListViewAdapter(Activity activity, int itemLayoutId) {
        this(activity, null, itemLayoutId, new ArrayList<T>());
    }

    public ListViewAdapter(Activity activity, int itemLayoutId, List<T> data) {
        this(activity, null, itemLayoutId, data);
    }

    public ListViewAdapter(Activity activity, Fragment fragment, int itemLayoutId,
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

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        if (mData != null && mData.size() > 0) {
            return mData.get(position);
        }
        return null;
    }

    /**
     * 获取布局类型总数
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    /**
     * 确定Item应使用何种布局
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 获取布局
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = getViewHolder(position, convertView, parent);
        onBindViewHolder(holder, position);
        return holder.getRootView();
    }

    /**
     * 填充ViewHolder中数据
     */
    public abstract void onBindViewHolder(ListViewHolder holder, int position);

    /**
     * 根据getItemViewType(position)方法,返回Item相应布局Id
     *
     * @param type getItemViewType(position)的返回值
     */
    public int getItemLayoutIdByItemViewType(int type) {
        return 0;
    }

    /**
     * 获取ViewHolder
     */
    private ListViewHolder getViewHolder(int position, View convertView,
                                         ViewGroup parent) {
        if (mItemLayoutId != 0) {
            return ListViewHolder.getViewHolder(mActivity, mFragment, position,
                    convertView, parent, mItemLayoutId);
        } else {
            return ListViewHolder.getViewHolder(mActivity, mFragment, position,
                    convertView, parent, getItemLayoutIdByItemViewType(getItemViewType
                            (position)));
        }
    }
}

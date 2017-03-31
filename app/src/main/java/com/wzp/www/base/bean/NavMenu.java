package com.wzp.www.base.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by wengzhipeng on 17/3/31.
 */
public class NavMenu {
    @JSONField(name = "groupId")
    public int mGroupId;
    @JSONField(name = "itemId")
    public int mItemId;
    @JSONField(name = "orderId")
    public int mOrderId;
    @JSONField(name = "title")
    public String mTitle;
    @JSONField(name = "icon")
    public String mIcon;

    public static List<NavMenu> getNavMenus(String jsonStr) {
        return JSON.parseArray(jsonStr, NavMenu.class);
    }
}

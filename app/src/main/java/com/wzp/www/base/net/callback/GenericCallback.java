package com.wzp.www.base.net.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * Created by wengzhipeng on 17/3/3.
 */

public abstract class GenericCallback<T> extends Callback<T> {

    @Override
    public T parseResponse(Response response) throws Exception {
        // 获取T的类型
        Class<T> TClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        if (TClass == String.class) {
            return (T) response.body().string();
        } else if (TClass == Reader.class) {
            return (T) response.body().charStream();
        } else if (TClass == InputStream.class) {
            return (T) response.body().byteStream();
        } else if (TClass == byte[].class) {
            return (T) response.body().bytes();
        } else if (TClass == Bitmap.class) {
            return (T) BitmapFactory.decodeStream(response.body().byteStream());
        } else {
            return JSON.parseObject(response.body().string(), TClass);
        }
    }
}

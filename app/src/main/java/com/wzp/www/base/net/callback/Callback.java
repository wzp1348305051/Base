package com.wzp.www.base.net.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wengzhipeng on 17/3/3.
 */

public abstract class Callback<T> {
    public abstract void onResponse(Call call, Response response, T parsedResponse);

    public abstract void onFailure(Call call, Exception e);

    public abstract T parseResponse(Response response) throws Exception;
}

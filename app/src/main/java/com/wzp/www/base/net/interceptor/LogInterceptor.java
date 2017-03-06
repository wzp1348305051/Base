package com.wzp.www.base.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 日志拦截器,通过OkHttpClient.Builder().addInterceptor(interceptor)或OkHttpClient.Builder()
 * .addNetworkInterceptor(interceptor)或okHttpClient.interceptors().add(interceptor)
 * 或okHttpClient.networkInterceptors().add(interceptor)可实现拦截网络请求
 *
 * @author wzp
 * @since 2017-03-02
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG = LogInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logRequest(request);
        Response response = chain.proceed(request);
        logResponse(response);
        return response;
    }

    /**
     * 记录请求信息
     */
    private void logRequest(Request request) {
        // TODO 自定义实现记录请求信息逻辑,如获取请求URL,请求头,请求体
    }

    /**
     * 记录响应信息
     */
    private void logResponse(Response response) {
        // TODO 自定义实现记录响应信息逻辑,如获取响应头,响应体
    }
}

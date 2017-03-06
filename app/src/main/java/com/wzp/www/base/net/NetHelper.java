package com.wzp.www.base.net;

import android.content.Context;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wzp.www.base.bean.constant.Global;
import com.wzp.www.base.helper.L;
import com.wzp.www.base.net.callback.Callback;
import com.wzp.www.base.net.interceptor.LogInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 网络请求工具类
 *
 * @author wzp
 * @since 2017-03-02
 */
public class NetHelper {
    private static final String TAG = NetHelper.class.getSimpleName();
    private static NetHelper sInstance;
    private static Context sContext;
    private OkHttpClient mClient;
    /**
     * 默认连接超时时间,单位毫秒
     */
    private static final long DEFAULT_CONNECT_TIMEOUT = 10000;
    /**
     * 默认读取超时时间,单位毫秒
     */
    private static final long DEFAULT_READ_TIMEOUT = 10000;
    private static final String CHARSET_UTF8 = "UTF-8";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;" +
            " charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse
            ("text/x-markdown; charset=utf-8");

    private NetHelper() {
        sContext = Global.APP_CONTEXT;
        mClient = new OkHttpClient.Builder().connectTimeout(DEFAULT_CONNECT_TIMEOUT,
                TimeUnit.MILLISECONDS).readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit
                .MILLISECONDS).addInterceptor(new LogInterceptor()).cookieJar(new
                PersistentCookieJar(new SetCookieCache(), new
                SharedPrefsCookiePersistor(sContext))).cache(new Cache(new File(Global
                .EXTERNAL_STORAGE_DIRECTORY_CACHE), 10 * 1024 * 1024)).build();
    }

    public static NetHelper getInstance() {
        if (sInstance == null) {
            synchronized (NetHelper.class) {
                if (sInstance == null) {
                    sInstance = new NetHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 重置默认OkHttpClient
     *
     * @param client 自定义OkHttpClient
     */
    public void setClient(OkHttpClient client) {
        if (client != null) {
            mClient = client;
        }
    }

    /**
     * 获取OkHttpClient
     */
    public OkHttpClient getClient() {
        return mClient;
    }

    /**
     * 添加应用拦截器
     *
     * @param interceptor 自定义Interceptor
     */
    public void addAppInterceptor(Interceptor interceptor) {
        if (mClient != null && interceptor != null) {
            mClient.interceptors().add(interceptor);
        }
    }

    /**
     * 添加网络拦截器
     *
     * @param interceptor 自定义Interceptor
     */
    public void addNetInterceptor(Interceptor interceptor) {
        if (mClient != null && interceptor != null) {
            mClient.networkInterceptors().add(interceptor);
        }
    }

    /**
     * 获取本地保存的Cookies,用于添加到请求头中(此操作会同时更新持久化的Cookie,删除已过期Cookie)
     */
    public List<Cookie> getCookies(HttpUrl url) {
        if (mClient != null) {
            return mClient.cookieJar().loadForRequest(url);
        }
        return null;
    }

    /**
     * 将响应体中的Cookies保存至本地,根据Cookie的persistent标识决定是否进行持久化操作
     */
    public void setCookies(HttpUrl url, List<Cookie> cookies) {
        if (mClient != null) {
            mClient.cookieJar().saveFromResponse(url, cookies);
        }
    }

    /**
     * 清空本地缓存的Cookies(同时清空内存Cookie和持久化Cookie)
     */
    public void clearCookies() {
        if (mClient != null) {
            CookieJar cookieJar = mClient.cookieJar();
            if (cookieJar instanceof ClearableCookieJar) {
                ((ClearableCookieJar) cookieJar).clear();
            }
        }
    }

    /**
     * 清空本次会话的Cookies,同时用持久化的Cookie更新内存Cookie
     */
    public void clearSession() {
        if (mClient != null) {
            CookieJar cookieJar = mClient.cookieJar();
            if (cookieJar instanceof ClearableCookieJar) {
                ((ClearableCookieJar) cookieJar).clearSession();
            }
        }
    }

    /**
     * 获取Url分隔符,用于在Url后拼接Query
     */
    private static String getUrlSeparator(String url) {
        String symbol;
        if (!url.contains("/")) {
            if (url.contains("%3F") && !url.contains("%3D")) {
                symbol = "";
            } else if (url.contains("%3F") && url.contains("%3D")) {
                symbol = "%26";
            } else {
                symbol = "%3F";
            }
        } else {
            if (url.contains("?") && !url.contains("=")) {
                symbol = "";
            } else if (url.contains("?") && url.contains("=")) {
                symbol = "&";
            } else {
                symbol = "?";
            }
        }
        return symbol;
    }

    /**
     * 拼接Url的Query请求参数
     *
     * @param url   请求地址
     * @param key   Query键
     * @param value Query值
     */
    public static String addUrlQuery(String url, String key, String value) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(key) && !TextUtils.isEmpty
                (value)) {
            try {
                StringBuffer buffer = new StringBuffer(url);
                buffer.append(getUrlSeparator(url)).append(URLEncoder.encode(key,
                        CHARSET_UTF8)).append("=").append(URLEncoder.encode(value,
                        CHARSET_UTF8));
                return buffer.toString();
            } catch (UnsupportedEncodingException e) {
                L.e(TAG, e.getMessage());
            }
        }
        return url;
    }

    /**
     * 拼接Url的Query请求参数
     *
     * @param url    请求地址
     * @param params Query键值对字典
     */
    public static String addUrlQuery(String url, Map<String, String> params) {
        if (!TextUtils.isEmpty(url) && params != null && params.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            try {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    String key = param.getKey();
                    String value = param.getValue();
                    if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                        buffer.append(URLEncoder.encode(key, CHARSET_UTF8)).append("=")
                                .append(URLEncoder.encode(value, CHARSET_UTF8)).append
                                ("&");
                    }
                }
                return url + getUrlSeparator(url) + buffer.substring(0, buffer.length()
                        - 1);
            } catch (UnsupportedEncodingException e) {
                L.e(TAG, e.getMessage());
            }
        }
        return url;
    }

    /**
     * 同步请求
     *
     * @param request 请求
     */
    public Response doSync(Request request) throws IOException {
        return mClient.newCall(request).execute();
    }

    /**
     * 异步请求
     *
     * @param request  请求
     * @param callback 请求回调
     */
    public void doAsync(Request request, final Callback callback) {
        mClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    try {
                        if (call.isCanceled()) {
                            callback.onFailure(call, new Exception("Canceled"));
                        } else if (!response.isSuccessful()) {
                            callback.onFailure(call, new Exception("bad request and " +
                                    "response code is " + response.code()));
                        } else {
                            callback.onResponse(call, response, callback.parseResponse
                                    (response));
                        }
                    } catch (Exception e) {
                        callback.onFailure(call, new Exception(e));
                        L.e(TAG, e.getMessage());
                    } finally {
                        ResponseBody body = response.body();
                        if (body != null) {
                            body.close();
                        }
                    }
                }
            }
        });
    }

    /**
     * 构造Request.Builder
     */
    private Request.Builder buildRequestBuilder(String url) {
        return new Request.Builder().url(url);
    }

    /**
     * 构造Request.Builder
     */
    private Request.Builder buildRequestBuilder(String url, Map<String, String> headers) {
        Request.Builder builder = buildRequestBuilder(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    builder.addHeader(key, value);
                }
            }
        }
        return builder;
    }

    /**
     * 构造FormBody
     */
    public FormBody buildFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                String key = param.getKey();
                String value = param.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    builder.add(key, value);
                }
            }
        }
        return builder.build();
    }

    /**
     * 同步Get请求
     *
     * @param url 请求地址
     */
    public Response doSyncGet(String url) throws IOException {
        return doSync(buildRequestBuilder(url).build());
    }

    /**
     * 同步Get请求
     *
     * @param url     请求地址
     * @param headers 请求头部字典
     */
    public Response doSyncGet(String url, Map<String, String> headers) throws
            IOException {
        return doSync(buildRequestBuilder(url, headers).build());
    }

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void doAsyncGet(String url, Callback callback) {
        doAsync(buildRequestBuilder(url).build(), callback);
    }

    /**
     * 异步Get请求
     *
     * @param url      请求地址
     * @param headers  请求头部字典
     * @param callback 请求回调
     */
    public void doAsyncGet(String url, Map<String, String> headers, Callback callback) {
        doAsync(buildRequestBuilder(url, headers).build(), callback);
    }

    /**
     * 同步Post请求
     *
     * @param url  请求地址
     * @param body 请求体
     */
    public Response doSyncPost(String url, RequestBody body) throws IOException {
        return doSync(buildRequestBuilder(url).post(body).build());
    }

    /**
     * 同步Post请求
     *
     * @param url     请求地址
     * @param headers 请求头部字典
     * @param body    请求体
     */
    public Response doSyncPost(String url, Map<String, String> headers, RequestBody
            body) throws IOException {
        return doSync(buildRequestBuilder(url, headers).post(body).build());
    }

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param body     请求体
     * @param callback 请求回调
     */
    public void doAsyncPost(String url, RequestBody body, Callback callback) {
        doAsync(buildRequestBuilder(url).post(body).build(), callback);
    }

    /**
     * 异步Post请求
     *
     * @param url      请求地址
     * @param headers  请求头部字典
     * @param body     请求体
     * @param callback 请求回调
     */
    public void doAsyncPost(String url, Map<String, String> headers, RequestBody body,
                            Callback callback) {
        doAsync(buildRequestBuilder(url, headers).post(body).build(), callback);
    }

    /**
     * 同步Post表单请求
     *
     * @param url    请求地址
     * @param params 表单键值对字典
     */
    public Response doSyncFormPost(String url, Map<String, String> params) throws
            IOException {
        return doSyncPost(url, buildFormBody(params));
    }

    /**
     * 同步Post表单请求
     *
     * @param url     请求地址
     * @param headers 请求头部字典
     * @param params  表单键值对字典
     */
    public Response doSyncFormPost(String url, Map<String, String> headers, Map<String,
            String> params) throws IOException {
        return doSyncPost(url, headers, buildFormBody(params));
    }

    /**
     * 异步Post表单请求
     *
     * @param url      请求地址
     * @param params   表单键值对字典
     * @param callback 请求回调
     */
    public void doAsyncFormPost(String url, Map<String, String> params, Callback
            callback) {
        doAsyncPost(url, buildFormBody(params), callback);
    }

    /**
     * 异步Post表单请求
     *
     * @param url      请求地址
     * @param headers  请求头部字典
     * @param params   表单键值对字典
     * @param callback 请求回调
     */
    public void doAsyncFormPost(String url, Map<String, String> headers, Map<String,
            String> params, Callback callback) {
        doAsyncPost(url, headers, buildFormBody(params), callback);
    }

    /**
     * 同步Post字符内容请求
     *
     * @param url       请求地址
     * @param mediaType 请求体类型
     * @param body      请求体内容
     */
    public Response doSyncStringPost(String url, MediaType mediaType, String body)
            throws IOException {
        return doSyncPost(url, RequestBody.create(mediaType, body));
    }

    /**
     * 同步Post字符内容请求
     *
     * @param url       请求地址
     * @param headers   请求头部字典
     * @param mediaType 请求体类型
     * @param body      请求体内容
     */
    public Response doSyncStringPost(String url, Map<String, String> headers, MediaType
            mediaType, String body)
            throws IOException {
        return doSyncPost(url, headers, RequestBody.create(mediaType, body));
    }

    /**
     * 异步Post字符内容请求
     *
     * @param url       请求地址
     * @param mediaType 请求体类型
     * @param body      请求体内容
     * @param callback  请求回调
     */
    public void doAsyncStringPost(String url, MediaType mediaType, String body,
                                  Callback callback) {
        doAsyncPost(url, RequestBody.create(mediaType, body), callback);
    }

    /**
     * 异步Post字符内容请求
     *
     * @param url       请求地址
     * @param headers   请求头部字典
     * @param mediaType 请求体类型
     * @param body      请求体内容
     * @param callback  请求回调
     */
    public void doAsyncStringPost(String url, Map<String, String> headers, MediaType
            mediaType, String body, Callback callback) {
        doAsyncPost(url, headers, RequestBody.create(mediaType, body), callback);
    }

    /**
     * 同步Post文件请求
     *
     * @param url       请求地址
     * @param mediaType 请求体类型
     * @param file      请求文件
     */
    public Response doSyncFilePost(String url, MediaType mediaType, File file)
            throws IOException {
        return doSyncPost(url, RequestBody.create(mediaType, file));
    }

    /**
     * 同步Post文件请求
     *
     * @param url       请求地址
     * @param headers   请求头部字典
     * @param mediaType 请求体类型
     * @param file      请求文件
     */
    public Response doSyncFilePost(String url, Map<String, String> headers, MediaType
            mediaType, File file)
            throws IOException {
        return doSyncPost(url, headers, RequestBody.create(mediaType, file));
    }

    /**
     * 异步Post文件请求
     *
     * @param url       请求地址
     * @param mediaType 请求体类型
     * @param file      请求文件
     * @param callback  请求回调
     */
    public void doAsyncFilePost(String url, MediaType mediaType, File file, Callback
            callback) {
        doAsyncPost(url, RequestBody.create(mediaType, file), callback);
    }

    /**
     * 异步Post文件请求
     *
     * @param url       请求地址
     * @param headers   请求头部字典
     * @param mediaType 请求体类型
     * @param file      请求文件
     * @param callback  请求回调
     */
    public void doAsyncStringPost(String url, Map<String, String> headers, MediaType
            mediaType, File file, Callback callback) {
        doAsyncPost(url, headers, RequestBody.create(mediaType, file), callback);
    }

    /**
     * 取消所有网络请求
     */
    public void cancelAll() {
        mClient.dispatcher().cancelAll();
    }

    /**
     * 根据请求tag取消网络请求
     *
     * @param tag 需要取消的请求的tag
     */
    public void cancelByTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            for (Call call : mClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : mClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 根据请求url取消网络请求
     *
     * @param url 需要取消的请求的url
     */
    public void cancelByUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            for (Call call : mClient.dispatcher().queuedCalls()) {
                if (url.equals(call.request().url().toString())) {
                    call.cancel();
                }
            }
            for (Call call : mClient.dispatcher().runningCalls()) {
                if (url.equals(call.request().url().toString())) {
                    call.cancel();
                }
            }
        }
    }

}

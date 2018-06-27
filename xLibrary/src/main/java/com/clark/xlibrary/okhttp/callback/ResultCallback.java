package com.clark.xlibrary.okhttp.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 结果回调
 *
 * T : 代表将结果解析成T类型
 *
 * Created by clark on 2017/8/3 15:24.
 */
public abstract class ResultCallback<T>{


    /**
     * 分线程中
     * 将response解析成T类型
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response) throws Exception;

    /**
     * UI线程中
     * 回调T类型结果
     */
    public abstract void onResponse(T response);

    /**
     * UI线程中
     */
    public abstract void onFailure(Call call, Exception e);
}
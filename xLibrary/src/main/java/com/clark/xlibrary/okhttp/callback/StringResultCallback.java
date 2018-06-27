package com.clark.xlibrary.okhttp.callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 将response解析成string类型
 *
 * Created by clark on 2017/8/3 16:10.
 */
public abstract class StringResultCallback extends ResultCallback<String>{

    @Override
    public String parseResponse(Response response) throws IOException {
        return response.body().string();
    }

}

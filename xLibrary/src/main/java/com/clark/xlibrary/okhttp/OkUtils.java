package com.clark.xlibrary.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.clark.xlibrary.okhttp.callback.ResultCallback;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by clark on 2017/8/2 14:16.
 */
public class OkUtils {
    private static final String TAG = "OkUtils";
    // 单利
    private static OkUtils okUtils = null;
    private OkHttpClient mOkHttpClient;
    // 主线程handler ，单利
    private static Handler mHandler;

    private OkUtils(){
        mOkHttpClient = new OkHttpClient();
    }

    public static OkUtils getIntance(){
        if(okUtils == null){
            return new OkUtils();
        }
        return okUtils;
    }

    public static Handler getHandler(){
        if(mHandler == null){
            return new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    private void get_async_(String uri, final ResultCallback resultCallback) {

        Request request = new Request.Builder()
                .url(uri)
                .get()
                .build();
        call(request, resultCallback);
    }

    private void get_async_(String uri, HashMap<String, String> map, ResultCallback resultCallback) {
        Request request = new Request.Builder()
                .url(build_Get_Uri(uri, map))
                .get()
                .build();
        call(request, resultCallback);
    }

    private void post_async_(String uri, HashMap<String, String> formMap, ResultCallback resultCallback) {

        Request request = new Request.Builder()
                .url(uri)
                .post(build_Post_FormBody(formMap))
                .build();
        call(request, resultCallback);
    }

    private void post_file_async_(String uri, File file, String fileKey, HashMap<String, String> formMap, ResultCallback resultCallback) {
        Request request = new Request.Builder()
                .url(uri)
                .post(build_Post_MultipartBody(new File[]{file}, new String[]{fileKey}, formMap))
                .build();
        call(request, resultCallback);
    }

    private void post_file_async_(String uri, File[] files, String[] fileKeys, ResultCallback resultCallback) {
        Request request = new Request.Builder()
                .url(uri)
                .post(build_Post_MultipartBody(files, fileKeys, null))
                .build();
        call(request, resultCallback);
    }

    private void post_file_async_(String uri, File[] files, String[] fileKeys, HashMap<String, String> fromMap, ResultCallback resultCallback) {
        Request request = new Request.Builder()
                .url(uri)
                .post(build_Post_MultipartBody(files, fileKeys, fromMap))
                .build();
        call(request, resultCallback);
    }

    /**
     * 拼凑get uri
     * @param uri
     * @param map
     * @return  www.baidu.com ? key=value & key=value & key=value
     */
    private String build_Get_Uri(String uri, HashMap<String, String> map){
        StringBuilder stringBuilder = new StringBuilder(uri);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        stringBuilder.append("?");
        boolean first_key = true;
        for (Map.Entry<String, String> entry : entries){
            if(first_key){
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                first_key = false;
            }else{
                stringBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        String result = stringBuilder.toString();
        Log.i(TAG, "buildGetUri: " + result);
        return result;
    }

    /**
     * 创建 post请求体
     * @param formMap
     * @return
     */
    private RequestBody build_Post_FormBody(HashMap<String, String> formMap) {
        Set<Map.Entry<String, String>> entries = formMap.entrySet();

        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : entries){
            formBuilder.addEncoded(entry.getKey(), entry.getValue());
        }
        return formBuilder.build();
    }

    /**
     * 创建 分块请求体
     * 文件上传、表单上传
     * @param files 要上传的文件
     * @param fileKeys 分块请求体中的name
     * @param formMap 要上传的表单
     * @return
     */
    private RequestBody build_Post_MultipartBody(File[] files, String[] fileKeys, HashMap<String, String> formMap) {
        // 分块请求体
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
        // 添加请求体 文件上传分块
        if(files != null){
            for (int i=0; i<files.length; i++){
                multiBuilder.addFormDataPart(fileKeys[i], files[i].getName(), RequestBody.create(MediaType.parse(get_file_mime_type(files[i])), files[i]));
            }
        }
        // 添加请求体 表单上传分块
        if(formMap != null){
            Set<String> keys = formMap.keySet();
            for (String key : keys){
                multiBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, formMap.get(key)));
            }
        }
        return multiBuilder.build();
    }

    /**
     * 返回文件 mime类型
     * 例如：文件 xxx.jpg 的mime类型为 image/jpg
     * @param file
     * @return
     */
    private String get_file_mime_type(File file){
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
        if(contentType == null){
            contentType = "application/octet-stream";
        }
        Log.i(TAG, file.getName() + " mime type = " + contentType);
        return contentType;
    }

    /**
     * 将request加入调队列
     * @param request
     * @param resultCallback
     */
    private void call(Request request, final ResultCallback resultCallback){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailureResult(call, e, resultCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    // 分线程中，解析response
                    Object o = resultCallback.parseResponse(response);
                    // 将解析结果回调
                    deliverSuccessResult(call, o, resultCallback);
                } catch (Exception e){
                    deliverFailureResult(call, e, resultCallback);
                } finally {
                    response.body().close();
                }

            }
        });
    }

    private void deliverSuccessResult(Call call, final Object o, final ResultCallback resultCallback){
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                resultCallback.onResponse(o);
            }
        });
    }

    private void deliverFailureResult(final Call call, final Exception e, final ResultCallback resultCallback){
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                resultCallback.onFailure(call, e);
            }
        });
    }

    /**
     * 异步 get
     * @param uri
     * @param resultCallback
     */
    public static void get_async(String uri, ResultCallback resultCallback){
        getIntance().get_async_(uri, resultCallback);
    }

    /**
     * 异步 get
     * @param uri
     * @param map 表单
     * @param resultCallback
     */
    public static void get_async(String uri, HashMap<String, String> map, ResultCallback resultCallback){
        getIntance().get_async_(uri, map, resultCallback);
    }

    /**
     * 异步 post
     * @param uri
     * @param fromMap 表单
     * @param resultCallback
     */
    public static void post_async(String uri, HashMap<String, String> fromMap, ResultCallback resultCallback){
        getIntance().post_async_(uri, fromMap, resultCallback);
    }

    /**
     * 异步 单文件、表单 上传
     * @param uri
     * @param file 要上传的文件
     * @param fileKey 分块请求体 头部中的name，非文件名
     * @param fromMap 表单
     * @param resultCallback
     */
    public static void post_file_async(String uri, File file, String fileKey, HashMap<String, String> fromMap, ResultCallback resultCallback){
        getIntance().post_file_async_(uri, file, fileKey, fromMap, resultCallback);
    }

    /**
     * 异步 多文件 上传
     * @param uri
     * @param files 要上传的文件
     * @param fileKeys 分块请求体 头部中的name，非文件名
     * @param resultCallback
     */
    public static void post_file_async(String uri, File[] files, String[] fileKeys, ResultCallback resultCallback){
        getIntance().post_file_async_(uri, files, fileKeys, resultCallback);
    }

    /**
     * 异步 多文件、表单 上传
     * @param uri
     * @param files 要上传的文件
     * @param fileKeys 分块请求体 头部中的name，非文件名
     * @param fromMap 表单
     * @param resultCallback
     */
    public static void post_file_async(String uri, File[] files, String[] fileKeys, HashMap<String, String> fromMap, ResultCallback resultCallback){
        getIntance().post_file_async_(uri, files, fileKeys, fromMap, resultCallback);
    }

}

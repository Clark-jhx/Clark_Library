package com.clark.xlibrary.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.Response;

/**
 * 将结果解析成bitmap
 *
 * Created by clark on 2017/8/3 16:40.
 */
public abstract class BitmapResultCallback extends ResultCallback<Bitmap> {
    @Override
    public Bitmap parseResponse(Response response) throws IOException {

        return BitmapFactory.decodeStream(response.body().byteStream());
    }
}

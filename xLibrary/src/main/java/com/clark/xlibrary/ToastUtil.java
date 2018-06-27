package com.clark.xlibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司工具类
 *
 * Created by clark on 2017/6/5 10:19.
 */
public class ToastUtil {
    /**
     * 短显示
     * @param context
     * @param str 字符串
     */
    public static void showShort(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT);
    }

    /**
     * 短显示
     * @param context
     * @param resId 资源id
     */
    public static void showShort(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 长显示
     * @param context
     * @param str 字符串
     */
    public static void showLong(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_LONG);
    }

    /**
     * 长显示
     * @param context
     * @param resId 资源id
     */
    public static void showLong(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示时长
     * @param context
     * @param resId 资源id
     * @param duration 显示时长
     */
    public static void showMine(Context context, int resId, int duration){
        Toast.makeText(context, resId, duration);
    }

    /**
     * 自定义显示时长
     * @param context
     * @param str 字符串
     * @param duration 显示时长
     */
    public static void showMine(Context context, String str, int duration){
        Toast.makeText(context, str, duration);
    }
}

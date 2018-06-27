package com.clark.xlibrary;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;

/**
 * 屏幕相关 信息
 *
 * Created by clark on 2017/6/2 14:18.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 返回手机 屏幕密度 (0.7 / 1.0 / 1.5 ..)
     * @param activity
     * @return
     */
    public static float getDensity(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return dm.density;
    }

    /**
     * 屏幕密度dpi (120 / 160 / 240 ..)
     * @param activity
     * @return
     */
    public static int getDensityDpi(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * 返回真实屏幕大小
     * 单位 px
     * @param activity
     * @return  (宽，高)
     */
    public static Pair getRealDiaplaySize(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return Pair.create(dm.widthPixels, dm.heightPixels);
    }


}

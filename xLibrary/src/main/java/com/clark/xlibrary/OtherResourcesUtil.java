package com.clark.xlibrary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 *
 * 获得其他应用资源
 * Created by clark on 2017/6/27 11:46.
 *
 * 参考文章：http://www.jianshu.com/p/3fc3a6040dac
 */
public class OtherResourcesUtil {

    /**
     * 获得其他应用的context
     *
     * Context.CONTEXT_IGNORE_SECURITY
     * Context.CONTEXT_INCLUDE_CODE
     *
     * @param context 当前应用的context
     * @param packageName 要获得的context 的应用的包名
     * @return 返回对应包名的context , can be null
     */
    public static Context getContextByPackageName(Context context, String packageName){
        if(context == null){
            return null;
        }
        try {
            Context packageContext = context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE);
            return packageContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得指定包名下的指定drawable资源
     *
     * 类似的可以获得color、string等各种资源。参考Resources有哪些get方法
     *
     * @param context 当前应用的context
     * @param packageName 想要获得的资源 所在的应用 的包名
     * @param resourceName 资源的名称
     * @return can be null
     */
    public static Drawable getDrawableByPackageName(Context context, String packageName, String resourceName){
        Context apkContext = getContextByPackageName(context, packageName);
        if(apkContext == null || TextUtils.isEmpty(resourceName)){
            return null;
        }
        Resources resources = apkContext.getResources();

        int resourcesId = resources.getIdentifier(resourceName, "drawable", packageName);// 参数2:资源的类型
        if(resourcesId != 0){
            return resources.getDrawable(resourcesId);// 注意资源的类型
        }
        return null;
    }

}


package com.clark.xlibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态 信息
 * Created by clark on 2017/6/2 15:02.
 */
public class NetStatusUtil {

    private static ConnectivityManager getConnectivityManager(Context context){
        return (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 判断 是否有网络连接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager mConnectivityManager = getConnectivityManager(context);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo != null){
            return mNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断 wifi网络是否可用
     * @return
     */
    public static boolean isWifiConnected(Context context){
        ConnectivityManager mConnectivityManager = getConnectivityManager(context);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo != null && mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return mNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断 Mobile网络是否可用
     * @return
     */
    public static boolean isMobileConnected(Context context){
        ConnectivityManager mConnectivityManager = getConnectivityManager(context);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo != null && mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            return mNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 网络类型
     * -1：未联网
     *  1: wifi连接
     * -3: 2G/3G网络
     * -4: 4G网络
     * @param context
     * @return
     */
    public static int getNetType(Context context){
        int netType = -1;
        ConnectivityManager mConnectivityManager = getConnectivityManager(context);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetworkInfo == null){
            return netType;
        }
        int type = mNetworkInfo.getType();
        if(type == ConnectivityManager.TYPE_WIFI){
            netType = 1;
        }else if(type == ConnectivityManager.TYPE_MOBILE){
            int subType = mNetworkInfo.getSubtype();
            TelephonyManager mTelephonyManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);

            if(subType == TelephonyManager.NETWORK_TYPE_LTE && !mTelephonyManager.isNetworkRoaming()){
                netType = -4;

            }else if(!mTelephonyManager.isNetworkRoaming()){
                netType = -3;
            }
        }
        return netType;
    }
}

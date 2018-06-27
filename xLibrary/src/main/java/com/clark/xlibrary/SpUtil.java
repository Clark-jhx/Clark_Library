package com.clark.xlibrary;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * sp 工具类
 * Created by clark on 2017/6/2 16:27.
 */
public class SpUtil {

    public static String spName = "sharepreferences_data";

    /**
     * 保存一个item
     * @param context
     * @param key
     * @param value
     */
    public static void putValue(Context context, String key, Object value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String)
        {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer)
        {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean)
        {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float)
        {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long)
        {
            edit.putLong(key, (Long) value);
        } else
        {
            edit.putString(key, value.toString());
        }
        SharedPreferencesCompat.apply(edit);
    }

    /**
     * 返回key对应的item值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getValue(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * 尽可能的使用apply代替commit，因为commit方法是同步的，并且
     * 我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步
     */
    private static class SharedPreferencesCompat{
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            }
            catch (IllegalArgumentException e) {}
            catch (IllegalAccessException e) {}
            catch (InvocationTargetException e) {}
            editor.commit();
        }
    }

}


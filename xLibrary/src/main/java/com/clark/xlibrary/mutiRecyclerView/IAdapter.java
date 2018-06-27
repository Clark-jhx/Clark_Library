package com.clark.xlibrary.mutiRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * T：该种布局对应的item数据类型
 * VH: 该种布局对应的ViewHolder
 * Created by clark on 2018/6/26.
 */

public abstract class IAdapter<T, VH extends RecyclerView.ViewHolder> {

    /**
     * 泛型T的Class
     */
    public Class get_T_Class() {
        Type type = this.getClass().getGenericSuperclass();
        if( type instanceof ParameterizedType ){
            ParameterizedType pt = (ParameterizedType) type;
            Type[] ts = pt.getActualTypeArguments();
            return (Class) ts[0]; // 第一个泛型T对应的类Class返回
        }else{
            return Object.class; // 若没有给定泛型，则返回Object类Class
        }

    }

    /**
     * 判断此布局是否可以处理该条item
     * @param item
     * @return
     */
    public abstract boolean canHandle(T item);

    /**
     * 返回这种类型布局对应的的ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 填充这种布局类型的ViewHolder
     * @param holder
     * @param position
     */
    public abstract void onBindViewHolder(VH holder, int position, T item);
}

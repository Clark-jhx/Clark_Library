package com.clark.xlibrary.mutiRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clark on 2018/6/26.
 */

public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int NO_ADAPTER = -1;

    // 数据
    private List<Object> items = new ArrayList<>();
    // 注册的adapter
    private List<IAdapter> mIAdapters = new ArrayList<>();
    // item没有对应adapter时，使用该布局
    private IAdapter noAdapter = new NoAdapter();

    @Override
    public int getItemViewType(int position) {
        // 找到可以处理该条item的Adapter，并返回索引
        for (int i=0; i<mIAdapters.size(); i++){
            Object item = items.get(position);
            IAdapter adapter = mIAdapters.get(i);
            // 返回泛型的Class
            Class clazz = adapter.get_T_Class();
            // 判断item数据类型是否是泛型类型
            if (clazz.isInstance(item)){
                // 判断该dapter是不是所对应的布局
                if (adapter.canHandle(clazz.cast(item)/*强转成泛型类型*/)){
                    return i;
                }
            }

        }
        // 没有找到可以处理的adapter
        return NO_ADAPTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NO_ADAPTER){
            return noAdapter.onCreateViewHolder(parent, viewType);
        }
        // 交由adapter处理
        return mIAdapters.get(viewType).onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == NO_ADAPTER){
            noAdapter.onBindViewHolder(holder, position, noAdapter.get_T_Class().cast(getItemId(position)));
            return;
        }
        // 交由adapter处理
        IAdapter adapter = mIAdapters.get(holder.getItemViewType());
        adapter.onBindViewHolder(holder, position, adapter.get_T_Class().cast(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void registerAdapter(IAdapter adapter){
        mIAdapters.add(adapter);
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}

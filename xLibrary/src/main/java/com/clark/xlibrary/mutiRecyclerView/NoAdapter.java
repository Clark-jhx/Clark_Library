package com.clark.xlibrary.mutiRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clark.xlibrary.R;

/**
 * item数据类型没有对应adapter时使用
 *
 * Created by clark on 2018/6/27.
 */

public class NoAdapter extends IAdapter<Object, NoAdapter.noViewHolder>{
    @Override
    public boolean canHandle(Object item) {
        return false;
    }

    @Override
    public noViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_adapter, parent, false);
        return new noViewHolder(view);
    }

    @Override
    public void onBindViewHolder(noViewHolder holder, int position, Object item) {
        // do nothing
    }

    static class noViewHolder extends RecyclerView.ViewHolder{

        public noViewHolder(View itemView) {
            super(itemView);
        }
    }
}

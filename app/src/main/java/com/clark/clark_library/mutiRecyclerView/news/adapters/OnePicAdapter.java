package com.clark.clark_library.mutiRecyclerView.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clark.clark_library.R;
import com.clark.clark_library.mutiRecyclerView.news.bean.News;
import com.clark.xlibrary.mutiRecyclerView.IAdapter;

/**
 * 一张图片的布局
 *
 * Created by clark on 2018/6/26.
 */

public class OnePicAdapter extends IAdapter<News, OnePicAdapter.OnePicViewHolder>{
    @Override
    public boolean canHandle(News item) {
        // 我能处理一张图片
        return item.type == 0;
    }

    @Override
    public OnePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_one_pic, parent, false);
        OnePicViewHolder holder = new OnePicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OnePicViewHolder holder, int position, News item) {
        holder.tvContent.setText(item.content);
        holder.tvSource.setText(item.source);
        holder.tvTime.setText(item.time);
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(0)).into(holder.ivPic);
    }

    static class OnePicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvSource;
        TextView tvTime;
        public OnePicViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

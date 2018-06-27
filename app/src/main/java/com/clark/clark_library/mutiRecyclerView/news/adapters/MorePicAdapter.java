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
 * 多张图片
 *
 * Created by clark on 2018/6/27.
 */

public class MorePicAdapter extends IAdapter<News, MorePicAdapter.MorePicViewHolder>{

    @Override
    public boolean canHandle(News item) {
        return item.type == 2;
    }

    @Override
    public MorePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_more_pic, parent, false);
        MorePicViewHolder holder = new MorePicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MorePicViewHolder holder, int position, News item) {
        holder.tvContent.setText(item.content);
        holder.tvSource.setText(item.source);
        holder.tvTime.setText(item.time);
        holder.tvCount.setText(item.count + " 图");
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(0)).into(holder.ivPic);
    }

    static class MorePicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvContent;
        TextView tvCount;
        TextView tvSource;
        TextView tvTime;
        public MorePicViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

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
 * 一个视频
 *
 * Created by clark on 2018/6/27.
 */

public class VideoAdapter extends IAdapter<News, VideoAdapter.VideoViewHolder>{

    @Override
    public boolean canHandle(News item) {
        return item.type == 3;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position, News item) {
        holder.tvContent.setText(item.content);
        holder.tvSource.setText(item.source);
        holder.tvTime.setText(item.time);
        holder.tvDuration.setText(item.duration);
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(0)).into(holder.ivPic);
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        TextView tvContent;
        TextView tvDuration;
        TextView tvSource;
        TextView tvTime;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

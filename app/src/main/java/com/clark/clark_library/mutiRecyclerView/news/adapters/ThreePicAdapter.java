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
 * 三张图片布局
 *
 * Created by clark on 2018/6/27.
 */

public class ThreePicAdapter extends IAdapter<News, ThreePicAdapter.ThreePicViewHolder>{

    @Override
    public boolean canHandle(News item) {
        return item.type == 1;
    }

    @Override
    public ThreePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_three_pic, parent, false);
        ThreePicViewHolder holder = new ThreePicViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ThreePicViewHolder holder, int position, News item) {
        holder.tvContent.setText(item.content);
        holder.tvSource.setText(item.source);
        holder.tvTime.setText(item.time);
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(0)).into(holder.ivPic1);
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(1)).into(holder.ivPic2);
        Glide.with(holder.itemView.getContext()).load(item.imgUrls.get(2)).into(holder.ivPic3);
    }

    static class ThreePicViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic1;
        ImageView ivPic2;
        ImageView ivPic3;
        TextView tvContent;
        TextView tvSource;
        TextView tvTime;
        public ThreePicViewHolder(View itemView) {
            super(itemView);
            ivPic1 = itemView.findViewById(R.id.iv_pic1);
            ivPic2 = itemView.findViewById(R.id.iv_pic2);
            ivPic3 = itemView.findViewById(R.id.iv_pic3);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}

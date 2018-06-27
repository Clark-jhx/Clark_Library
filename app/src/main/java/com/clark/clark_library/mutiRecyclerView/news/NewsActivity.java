package com.clark.clark_library.mutiRecyclerView.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.clark.clark_library.R;
import com.clark.clark_library.mutiRecyclerView.utils.LocalFileUtils;
import com.clark.clark_library.mutiRecyclerView.news.adapters.MorePicAdapter;
import com.clark.clark_library.mutiRecyclerView.news.adapters.OnePicAdapter;
import com.clark.clark_library.mutiRecyclerView.news.adapters.ThreePicAdapter;
import com.clark.clark_library.mutiRecyclerView.news.adapters.VideoAdapter;
import com.clark.clark_library.mutiRecyclerView.news.bean.News;
import com.clark.xlibrary.mutiRecyclerView.BaseAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 列表中的数据类型都相同，显示的布局不同
 */
public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BaseAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = this.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // 设置Adapter
        baseAdapter = new BaseAdapter();
        // 添加各个adapter
        baseAdapter.registerAdapter(new OnePicAdapter());
        baseAdapter.registerAdapter(new ThreePicAdapter());
        baseAdapter.registerAdapter(new VideoAdapter());
        baseAdapter.registerAdapter(new MorePicAdapter());

        recyclerView.setAdapter(baseAdapter);

        // 初始化数据
        initData();
    }

    private void initData(){
        String newsListStr = LocalFileUtils.getStringFormAsset(this, "news.json");
        List<Object> newsList = new Gson().fromJson(newsListStr, new TypeToken<List<News>>() {
        }.getType());
        baseAdapter.setItems(newsList);
    }
}

package com.clark.clark_library.mutiRecyclerView.news.bean;

import java.util.List;

/**
 * Created by clark on 2018/6/26.
 */

public class News {
    public int type = 0; // 0:一张图片；1:三张图片；2:多张图片；3:视频类型
    public List<String> imgUrls = null;
    public String content = "";
    public String count = "";
    public String duration = "";
    public String source = "";
    public String time = "";
    public String link = "";
}

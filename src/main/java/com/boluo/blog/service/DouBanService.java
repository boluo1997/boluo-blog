package com.boluo.blog.service;

import com.boluo.blog.dao.DouBanDao;
import com.boluo.blog.domain.Movie;
import com.boluo.blog.utils.GetJson;
import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DouBanService {

    @Autowired
    private DouBanDao douBanDao;

    public void downDouBanTop() throws Exception {

        JSONObject resultJson = GetJson.getHttpJson("https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&sort=recommend&page_limit=20&page_start=0", 1);
        Preconditions.checkArgument(!Objects.isNull(resultJson), "无结果!");
        JSONArray subjects = resultJson.getJSONArray("subjects");

        Movie movie = new Movie();
        for (int i = 0; i < subjects.length(); ++i) {

            JSONObject jn = subjects.getJSONObject(i);

            String id = jn.getString("id");
            String title = jn.getString("title");
            String cover = jn.getString("cover");
            String rate = jn.getString("rate");

            movie.setId(id);
            movie.setTitle(title);
            movie.setCover(cover);
            movie.setRate(rate);

        }
    }
}

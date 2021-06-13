package com.boluo.blog.service;

import com.boluo.blog.dao.JapanVideoDao;
import com.boluo.blog.domain.JapanMovie;
import com.boluo.blog.utils.GetJson;
import com.clearspring.analytics.util.Lists;
import com.google.common.base.Preconditions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Objects;


@Service
public class JapanVideoService {

    @Autowired
    private JapanVideoDao japanVideoDao;

    public void download() throws Exception {

        // 该明星下共有几页的电影
        int pageAmount = 4;

        for (int i = 0; i < pageAmount; ++i) {

            String sUrl = "https://www.4hucc45.com/av/ssyy/";
            int num = i + 1;
            if (i > 0) sUrl += "index_" + num + ".html";

            Document movieList = Jsoup.parse(new URL(sUrl), 10000);

            List<JapanMovie> insertMovieList = Lists.newArrayList();
            Elements movies = movieList.select("div.row.col5.clearfix > dl > dt > a");
            Elements moviesCover = movieList.select("div.row.col5.clearfix > dl > dt > a > img");

            for (int j = 0; j < movies.size(); j++) {

                JapanMovie japanMovie = new JapanMovie();
                String videoId = movies.get(j).attr("href");
                String title = movies.get(j).attr("title");
                String cover = moviesCover.get(j).attr("data-original");

                japanMovie.setVideoId(videoId);
                japanMovie.setTitle(title);
                japanMovie.setCover(cover);
                insertMovieList.add(japanMovie);
            }

            for (JapanMovie jm : insertMovieList) {
                Document movieInfo = Jsoup.parse(new URL("https://www.4hucc45.com/" + jm.getVideoId()), 10000);
                String url = movieInfo.getElementById("url").val();
                jm.setUrl(url);

                // 逐条存入数据库
                // System.out.println(jm);
                // japanVideoDao.insertMovie(jm);
                // System.out.println("存储成功...");
            }

            // 分批存入数据库
            System.out.println(insertMovieList);
            japanVideoDao.insertMovies(insertMovieList);
            System.out.println("存储完毕...");
        }
        System.out.println("全部存储完毕...");
    }


}

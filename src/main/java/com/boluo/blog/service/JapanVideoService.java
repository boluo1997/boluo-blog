package com.boluo.blog.service;

import com.boluo.blog.dao.JapanVideoDao;
import com.boluo.blog.domain.JapanMovie;
import com.boluo.blog.utils.DownloadUtils;
import com.boluo.blog.utils.GetJson;
import com.clearspring.analytics.util.Lists;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class JapanVideoService {

    private static final Logger logger = LoggerFactory.getLogger(JapanVideoService.class);

    @Autowired
    private JapanVideoDao japanVideoDao;

    public void download() throws Exception {

        // 该明星下共有几页的电影
        int pageAmount = 13;

        for (int i = 0; i < pageAmount; ++i) {

            // String sUrl = "https://www.4hucc45.com/av/ssyy/";
            String sUrl = "https://www.4hubb48.com/av/stym/";
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

    // 下载视频到本地
    public void downloadStart() {

        //设置https协议访问
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        // 下载目录
        String DOWNLOAD_DIR = "G:/video/";

        List<JapanMovie> movies = japanVideoDao.selectMovies();
        for(JapanMovie jm : movies){
            Map<String, String> hashMap = Maps.newHashMap();
            hashMap.put(jm.getTitle(), jm.getUrl());
            logger.debug("正在下载..." + jm.getTitle() + ", " + jm.getUrl());
            DownloadUtils.downloadMovie(DOWNLOAD_DIR, hashMap);
            // japanVideoDao.updateMovieStatus(jm.getUrl());
            logger.debug("状态已更新...");
        }

    }
}

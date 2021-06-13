import com.boluo.blog.domain.JapanMovie;
import com.boluo.blog.utils.GetJson;
import com.clearspring.analytics.util.Lists;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JapanVideoTest {

    public static void main(String[] args) throws Exception {

        Document movieList = Jsoup.parse(new URL("https://www.4hucc45.com/av/ssyy/"), 1000);

        List<JapanMovie> insertMovieList = Lists.newArrayList();
        Elements movies = movieList.select("div.row.col5.clearfix > dl > dt > a");
        Elements moviesCover = movieList.select("div.row.col5.clearfix > dl > dt > a > img");

        for (int i = 0; i < movies.size(); i++) {

            JapanMovie japanMovie = new JapanMovie();
            String videoId = movies.get(i).attr("href");
            String title = movies.get(i).attr("title");
            String cover = moviesCover.get(i).attr("data-original");

            japanMovie.setVideoId(videoId);
            japanMovie.setTitle(title);
            japanMovie.setCover(cover);
            insertMovieList.add(japanMovie);
        }

        for (JapanMovie jm : insertMovieList) {
            Document movieInfo = Jsoup.parse(new URL("https://www.4hucc45.com/" + jm.getVideoId()), 1000);
            String url = movieInfo.getElementById("url").val();
            jm.setUrl(url);
            System.out.println(jm);
        }

    }

}

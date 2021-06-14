package com.boluo.blog.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadUtils {

    /**
     * 开启多线程下载
     *
     * @param DOWNLOAD_DIR
     * @param urlMap
     */
    public static void downloadMovie(final String DOWNLOAD_DIR, Map<String, String> urlMap) {
        ExecutorService es = Executors.newFixedThreadPool(8);
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            final String title = entry.getKey();// 视频名称
            final String url = entry.getValue();// 视频url

            es.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println("正在下载:    " + title + ".......");
                        File destFile = new File(DOWNLOAD_DIR + title + ".mp4");

                        download(url, destFile);
                        System.out.println("=========> " + title + " 下载完毕!");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 通过视频的URL下载该视频并存入本地
     *
     * @param url      视频的URL
     * @param destFile 视频存入的文件夹
     * @throws IOException
     */
    private static void download(String url, File destFile) throws IOException {
        URL videoUrl = new URL(url);

        InputStream is = videoUrl.openStream();
        FileOutputStream fos = new FileOutputStream(destFile);

        int len = 0;
        byte[] buffer = new byte[1024];
        while ((-1) != (len = is.read(buffer))) {
            fos.write(buffer, 0, len);
        }
        fos.flush();

        if (null != fos) {
            fos.close();
        }

        if (null != is) {
            is.close();
        }
    }
}

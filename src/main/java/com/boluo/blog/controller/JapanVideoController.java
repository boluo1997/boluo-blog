package com.boluo.blog.controller;

import com.boluo.blog.service.DouBanService;
import com.boluo.blog.service.JapanVideoService;
import com.boluo.blog.utils.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("japan")
public class JapanVideoController {

    @Autowired
    private JapanVideoService japanVideoService;

    // 爬取电影到本地
    @RequestMapping("download")
    public SysResult downDouBanTop() {
        try {
            japanVideoService.download();
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.error();
        }
    }

    // 下载电影到本地
    @RequestMapping("downloadStart")
    public SysResult downloadStart() {
        try {
            japanVideoService.downloadStart();
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.error();
        }
    }
}

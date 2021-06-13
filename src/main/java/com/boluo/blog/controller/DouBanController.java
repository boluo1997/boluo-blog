package com.boluo.blog.controller;

import com.boluo.blog.service.DouBanService;
import com.boluo.blog.utils.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("douban")
public class DouBanController {


    @Autowired
    private DouBanService douBanService;

    // 爬取top250电影到本地
    @RequestMapping("articleCount")
    public SysResult downDouBanTop() {
        try {
            douBanService.downDouBanTop();
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.error();
        }
    }

}

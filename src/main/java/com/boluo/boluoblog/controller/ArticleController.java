package com.boluo.boluoblog.controller;


import com.boluo.boluoblog.service.ArticleService;
import com.boluo.boluoblog.utils.EasyUIResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    private ArticleService articleService;


    //统计文章数量
    @RequestMapping("articleCount")
    public Integer articleCount(){
        return articleService.articleCount();
    }


    //查看文章
    @RequestMapping("check")
    public EasyUIResult checkArticle(Integer page,Integer rows){
        return articleService.checkArticle(page,rows);
    }


    //查看最新文章
    @RequestMapping("latestArticles")
    public List LatestArticles(){
        return articleService.LatestArticles();
    }






}

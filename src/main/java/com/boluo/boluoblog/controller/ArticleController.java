package com.boluo.boluoblog.controller;


import com.boluo.boluoblog.service.ArticleService;
import com.boluo.boluoblog.utils.EasyUIResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article")
public class ArticleController {

    private ArticleService articleService;


    //查看文章
    @RequestMapping("check")
    public EasyUIResult checkArticle(Integer page,Integer rows){
        return articleService.checkArticle(page,rows);
    }









}

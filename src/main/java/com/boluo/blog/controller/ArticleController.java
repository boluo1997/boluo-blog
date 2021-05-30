package com.boluo.blog.controller;

import com.boluo.blog.service.ArticleService;
import com.boluo.blog.utils.EasyUIResult;
import com.boluo.blog.utils.RedisUtils;
import com.boluo.blog.utils.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisUtils redisUtils;

    //统计文章数量
    @RequestMapping("articleCount")
    public Integer articleCount(){
        return articleService.articleCount();
    }

    //查看文章
    @RequestMapping("checkArticles")
    public EasyUIResult checkArticle(Integer page,Integer rows){
        return articleService.checkArticle(page,rows);
    }

    //查看文章信息
//    @RequestMapping("item/{articleId}")
//    public String checkArticleInfo(@PathVariable Integer articleId){
//
//        boolean br = redisUtils.exists(articleId.toString());
//        String str = "";
//
//        if(br){
//            System.out.println("从缓存中获取数据...");
//            Object object = redisUtils.get(articleId.toString());
//            str = object.toString();
//        }else {
//            System.out.println("从数据库中获取数据...");
//            str = articleService.checkArticleInfo(articleId);
//            redisUtils.set(articleId.toString(), str);
//        }
//
//        return str;
//    }

    //查看文章信息
    @RequestMapping("item/{articleId}")
    public SysResult checkArticleInfo(@PathVariable Integer articleId){
        SysResult result = new SysResult();
        result.setStatus(200);
        result.setMsg("OK");

        return result;
    }

    //查看最新文章
    @RequestMapping("latestArticles")
    public List LatestArticles(){
        return articleService.LatestArticles();
    }


    //查看文章分类数





}

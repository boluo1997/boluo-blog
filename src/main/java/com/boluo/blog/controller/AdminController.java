package com.boluo.blog.controller;

import com.boluo.blog.domain.Article;
import com.boluo.blog.service.AdminService;
import com.boluo.blog.utils.SysResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    private AdminService adminService;

    //添加文章
    @RequestMapping("addArticle")
    public void articleCount(Article article){
        try{
            adminService.addArticle(article);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改文章
    // test


    //删除文章
    @RequestMapping("deleteArticle")
    public SysResult deleteArticle(Integer articleId){
        try{
            adminService.deleteArticle(articleId);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.error();
        }
    }


}

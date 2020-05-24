package com.boluo.boluoblog.service;

import com.boluo.boluoblog.dao.AdminDao;
import com.boluo.boluoblog.domain.Article;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminDao adminDao;


    //添加文章
    public void addArticle(Article article) {
        adminDao.addArticle(article);
    }


    //删除文章
    public void deleteArticle(Integer articleId) {
        adminDao.deleteArticle(articleId);
    }
}

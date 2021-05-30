package com.boluo.blog.service;

import com.boluo.blog.dao.AdminDao;
import com.boluo.blog.domain.Article;
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

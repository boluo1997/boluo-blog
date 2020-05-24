package com.boluo.boluoblog.dao;

import com.boluo.boluoblog.domain.Article;

public interface AdminDao {

    //添加文章
    void addArticle(Article article);

    //删除文章
    void deleteArticle(Integer articleId);
}

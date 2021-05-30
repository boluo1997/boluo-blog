package com.boluo.blog.dao;

import com.boluo.blog.domain.Article;

public interface AdminDao {

    //添加文章
    void addArticle(Article article);

    //删除文章
    void deleteArticle(Integer articleId);
}

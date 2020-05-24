package com.boluo.boluoblog.dao;

import com.boluo.boluoblog.domain.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {

    //分页查看文章之前,先查询文章表中数量
    Integer selectArticleCount();

    //分页查看文章
    List<Article> selectArticleByPage(@Param("start") Integer start,@Param("rows") Integer rows);
}

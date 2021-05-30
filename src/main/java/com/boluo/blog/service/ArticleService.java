package com.boluo.blog.service;


import com.boluo.blog.dao.ArticleDao;
import com.boluo.blog.domain.Article;
import com.boluo.blog.utils.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    //统计文章数量
    public Integer articleCount() {
        return articleDao.selectArticleCount();
    }


    //查看文章
    public EasyUIResult checkArticle(Integer page, Integer rows) {
        //准备一个返回对象
        EasyUIResult result = new EasyUIResult();
        //封装total
        Integer total = articleDao.selectArticleCount();
        result.setTotal(total);

        //封装返回分页数据rows          List<Article>
        Integer start = (page-1)*rows;
        List<Article> aList = articleDao.selectArticleByPage(start,rows);
        result.setRows(aList);
        return result;
    }

    //查看最新文章
    public List LatestArticles(){
        List<Article> aList = articleDao.LatestArticles();
        return aList;
    }

    // 查看单个文章
    public String checkArticleInfo(Integer articleId) {
        return articleDao.selectArticleInfo(articleId);
    }
}

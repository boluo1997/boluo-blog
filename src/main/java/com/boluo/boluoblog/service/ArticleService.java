package com.boluo.boluoblog.service;


import com.boluo.boluoblog.dao.ArticleDao;
import com.boluo.boluoblog.domain.Article;
import com.boluo.boluoblog.utils.EasyUIResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private ArticleDao articleDao;

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



}

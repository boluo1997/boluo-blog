package com.boluo.boluoblog.domain;

import java.util.Date;

public class Article {

    private Integer articleId;      //文章主键ID

    private String articleTitle;    //标题

    private String articleImage;    //标题图片

    //内容缩略名

    private Date articleCreateTime;     //文章创建时间

    private Date articleModifyTime;     //文章修改时间

    private String articleContent;      //文章文字内容

    //内容所属用户ID

    private String articleType;         //内容类别

    //内容状态

    //标签列表

    //分类列表

    private Integer hits;               //点击次数

    //内容所属评论数

    //是否允许评论

    //是否允许ping

    //允许出现在聚合中


}

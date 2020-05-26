package com.boluo.boluoblog.domain;

import java.util.Date;

public class Article {

    private Integer id;              //文章主键ID

    private String title;           //标题

    private String image;           //标题图片

    //内容缩略名

    private Date cTime;             //文章创建时间

    private Date mTime;             //文章修改时间

    private String content;         //文章文字内容

    //内容所属用户ID

    private String type;            //文章分类

    private String tag;                 //标签

    //内容状态

    //分类列表

    private Integer hits;               //点击次数

    private Integer likes;              //点赞数

    //内容所属评论数

    //是否允许评论

    //是否允许ping

    //允许出现在聚合中

    private Integer status;             //状态码

    private Integer isdelete;           //是否删除


}

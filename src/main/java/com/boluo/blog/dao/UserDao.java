package com.boluo.blog.dao;

import com.boluo.blog.domain.UserEntity;
import com.boluo.blog.request.BasePageRequest;
import com.boluo.blog.request.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    List<UserEntity> selectUserInfoList(@Param("request") BasePageRequest request, @Param("page") PageHelper pager);

    Integer countUserInfoList(@Param("request") BasePageRequest request, @Param("page") PageHelper pager);
}

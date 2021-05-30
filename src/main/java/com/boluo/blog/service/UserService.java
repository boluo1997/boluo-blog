package com.boluo.blog.service;

import com.boluo.blog.dao.UserDao;
import com.boluo.blog.domain.UserEntity;
import com.boluo.blog.request.BasePageRequest;
import com.boluo.blog.request.PageHelper;
import com.boluo.blog.response.BasePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public BasePageResponse<UserEntity> selectUserInfoList(BasePageRequest request) {

        BasePageResponse<UserEntity> response = new BasePageResponse<>();

        PageHelper pager = null;
        if (request.getPageNo() > 0 || request.getCount() > 0) {
            pager = new PageHelper(request.getPageNo(), request.getCount());
        }

        response.setData(userDao.selectUserInfoList(request, pager));
        response.setCount(userDao.countUserInfoList(request, pager));

        return response;
    }
}

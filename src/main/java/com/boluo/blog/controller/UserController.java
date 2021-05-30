package com.boluo.blog.controller;

import com.boluo.blog.request.BasePageRequest;
import com.boluo.blog.response.BasePageResponse;
import com.boluo.blog.domain.UserEntity;
import com.boluo.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/getUserInfoList")
    public BasePageResponse<UserEntity> getUserInfoList(@RequestBody BasePageRequest request) {
        return userService.selectUserInfoList(request);
    }

}

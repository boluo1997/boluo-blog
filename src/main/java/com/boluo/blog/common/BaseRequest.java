package com.boluo.blog.common;

public class BaseRequest {

    // @ApiModelProperty(required = false,hidden=true)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

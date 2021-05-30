package com.boluo.blog.common;

// import io.swagger.annotations.ApiModelProperty;

public class BasePageRequest extends BaseRequest {
    private static final long serialVersionUID = 3568883075444378337L;


    // @ApiModelProperty(value = "页码")
    private Integer pageNo = 1;

    // @ApiModelProperty(value = "页面显示记录数")
    private Integer count = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}

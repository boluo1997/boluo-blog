package com.boluo.blog.response;

import io.swagger.annotations.ApiModel;
import com.boluo.blog.enums.SystemResultCode;

import java.io.Serializable;

@ApiModel(description = "响应参数")
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -6259241733322453783L;

    private ResultInfo resultInfo;

    private T data;

    public BaseResponse() {
        resultInfo = new ResultInfo();
    }

    public BaseResponse(T data) {
        this();
        this.data = data;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setResultEnum(SystemResultCode resultEnum) {
        this.resultInfo.setResultEnum(resultEnum);
    }
}

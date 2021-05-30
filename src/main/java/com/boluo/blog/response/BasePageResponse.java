package com.boluo.blog.response;

// import io.swagger.annotations.ApiModel;

import com.boluo.blog.request.PageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:BasePageResponse <br/>
 * Function: 带分页数据的接口响应基类 <br/>
 * Reason: 带分页数据的接口响应基类 <br/>
 * Date: 2018年4月24日 下午7:43:28 <br/>
 *
 * @author java
 * @see
 * @since JDK 1.8
 */
// @ApiModel(description = "分页响应参数")
public class BasePageResponse<T> extends BaseResponse<List<T>> {

    private static final long serialVersionUID = -7555741211218226533L;

    protected Integer total = 0;

    protected List<T> data;

    protected Integer pageNo = 1;

    protected Integer count = 0;

    protected String search;

    protected Integer totalPage = 1;

    public BasePageResponse() {
        // 默认20条
        this.count = 20;
        data = new ArrayList<>();
    }

    public BasePageResponse(int count) {
        this.count = count;
    }

    public BasePageResponse(PageHelper pageHelper) {
        if (pageHelper == null) {
            return;
        }
        this.count = pageHelper.getLength();
        this.pageNo = pageHelper.getPageNo();
        this.total = pageHelper.getTotalRecords();
        this.totalPage = pageHelper.getPageCount();
    }

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getTotalPage() {
        if (count == 0) {
            return 1;
        }
        return (total - 1) / count + 1;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }
}
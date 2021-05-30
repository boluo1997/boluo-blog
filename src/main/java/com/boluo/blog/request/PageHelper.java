package com.boluo.blog.request;

import java.io.Serializable;

/**
 * ClassName:PageHelper <br/>
 * Function: 用于处理数据库查询分页的工具类. <br/>
 * Reason: 为系统自动生成代码引入 <br/>
 * Date: 2021年5月30日 下午20:15:58 <br/>
 *
 * @author java
 * @version
 * @since JDK 1.8
 * @see
 */
public class PageHelper implements Serializable {

    private static final long serialVersionUID = 1L;
    private int begin;
    private int end;
    private int length=20;
    private int totalRecords;
    private int pageNo;
    private int pageCount;
    private String sidx;
    private String order;


    public PageHelper(int pageNo, int length) {

        this.length = length;
        initByPage(pageNo);
    }

    public PageHelper(int begin, int length, int totalRecords) {
        this(begin, length);
        this.totalRecords = totalRecords;
    }

    public PageHelper(int pageNo) {
        initByPage(pageNo);
    }

    private void initByPage(int pageNo) {
        this.pageNo = pageNo;
        pageNo = (pageNo > 0) ? pageNo : 1;
        this.begin = (this.length * (pageNo - 1));
        this.end = (this.length * pageNo);
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setBegin(int begin) {
        this.begin = begin;
        if (this.length == 0)
            return;
        this.pageNo = ((int) Math.floor(this.begin * 1.0D / this.length) + 1);
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
        if (this.begin == 0)
            return;
        this.pageNo = ((int) Math.floor(this.begin * 1.0D / this.length) + 1);
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        this.pageCount = (int) Math.floor(this.totalRecords * 1.0D / this.length);
        if (this.totalRecords % this.length == 0)
            return;
        this.pageCount += 1;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        initByPage(pageNo);
    }

    public int getPageCount() {
        if (this.pageCount == 0) {
            return 1;
        }
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("begin=").append(this.begin).append(", end=")
                .append(this.end).append(", length=").append(this.length).append(", totalRecords=")
                .append(this.totalRecords).append(", pageNo=").append(this.pageNo).append(", pageCount=")
                .append(this.pageCount).append(", sidx=").append(this.sidx).append(", order=")
                .append(this.order);

        return builder.toString();
    }

    public static PageHelper getPageHelper(BasePageRequest req) {
        PageHelper pager = null;
        if ((req.getCount() > 0 || req.getPageNo() >0)) {
            pager = new PageHelper(req.getPageNo(), req.getCount());
        }
        return pager;
    }

}


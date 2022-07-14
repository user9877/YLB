package com.bjpowernode.web.resp;

/**
 * ClassName:PageInfo
 * Package:com.bjpowernode.web.resp
 * Date:2022/7/12 17:53
 * Description:
 * Autor:FH
 */

public class PageInfo {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalRecord;

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        if(totalRecord != null && (pageSize != null && pageSize > 0)){
            if(totalRecord % pageSize == 0){
                this.totalPage = totalRecord/pageSize;
            }else{
                this.totalPage = totalRecord/pageSize+1;
            }
        }
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer totalPage, Integer totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
    }

    public PageInfo() {
    }



    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}

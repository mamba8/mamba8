package com.jztey.framework.mvc;

public class Paging {
    private int page = 1;
    private int pageSize = 10;
    private int total = -1;

    public Paging() {
    }

    public Paging(int page, int pageSize) {
        this();
        this.page = page;
        this.pageSize = pageSize;
    }

    public boolean isNeedSetTotal() {
        return -1 == total;
    }

    /**
     * startIndex 从0开始
     * @return
     */
    public int getStartIndex() {
        return (page - 1) * pageSize;
    }

    /**
     * 当页包含startIndex 至 endIndex, 两端闭区间
     * @return
     */
    public int getEndIndex() {
        return this.getStartIndex() + this.getPageSize() - 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

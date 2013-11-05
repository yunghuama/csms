package com.platform.vo;

import java.util.List;

/**
 * 分页结果类
 * 
 * @author Marker.King
 * 
 * @param <T>
 */
public class Page<T> {

    private List<T> allResult; // 所有结果
    private List<T> list; // 数据结果
    private int pageSize = 20; // 每页行数
    private int currPage = 1; // 当前页数
    private int maxPage; // 最大页数
    private int rowCount; // 总行数
    private String formId; // 表单id
    private String url; // 分页方法地址

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<T> getAllResult() {
        return allResult;
    }

    public void setAllResult(List<T> allResult) {
        this.allResult = allResult;
    }
}
package com.platform.domain;

public class Dictionary extends BaseDomain {

    private static final long serialVersionUID = -5235338588177936219L;

    private String name; // 字典名称
    private String superId; // 所属父字典ID
    private Integer sortNum; // 排序号
    private String remark; // 说明

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperId() {
        return this.superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public Integer getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
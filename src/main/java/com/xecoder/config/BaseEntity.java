package com.xecoder.config;

import javax.persistence.*;
import java.io.Serializable;

public class BaseEntity implements Serializable {
    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 100;

    @Transient
    private String sort ;

    @Transient
    private String order = "desc";

    @Transient
    private Integer sum = 0;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}

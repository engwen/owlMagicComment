package com.owl.mvc.vo;

import com.owl.magicUtil.model.ModelPrototype;

/**
 * 排序基类
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/06/15.
 */
public final class ReorderVO<T> extends ModelPrototype {
    //序号
    private Integer order;

    private T model;

    public ReorderVO(Integer order, T model) {
        this.order = order;
        this.model = model;
    }

    public ReorderVO() {
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}

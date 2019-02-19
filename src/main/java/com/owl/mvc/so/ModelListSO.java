package com.owl.mvc.so;

import java.util.List;

/**
 * 查询、删除使用 对象集合
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/8.
 */
public class ModelListSO<T> {
    private List<T> modelList;

    private ModelListSO(List<T> modelList) {
        this.modelList = modelList;
    }

    public static <T> ModelListSO<T> getInstance(List<T> modelList) {
        return new ModelListSO<T>(modelList);
    }

    public List<T> getModelList() {
        return modelList;
    }

    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }
}

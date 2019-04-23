package com.owl.mvc.so;

/**
 * 标准对象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/12.
 */
public class ModelSO<T> {
    private T model;

    private ModelSO(T model) {
        this.model = model;
    }

    public static <T> ModelSO<T> getInstance(T model) {
        return new ModelSO<>(model);
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}

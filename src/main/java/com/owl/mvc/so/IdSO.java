package com.owl.mvc.so;

/**
 * id 類
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/21.
 */
public class IdSO<ID> {
    private ID id;

    public static <ID> IdSO getInstance(ID id) {
        return new IdSO<>(id);
    }

    public IdSO() {
    }

    public IdSO(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}

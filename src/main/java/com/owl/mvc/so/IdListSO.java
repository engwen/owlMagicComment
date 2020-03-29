package com.owl.mvc.so;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/8.
 */
public class IdListSO<ID> {
    private List<ID> idList;

    public IdListSO(List<ID> idList) {
        this.idList = idList;
    }

    public static <ID> IdListSO getInstance(List<ID> idList) {
        return new IdListSO<>(idList);
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void setIdList(List<ID> idList) {
        this.idList = idList;
    }
}

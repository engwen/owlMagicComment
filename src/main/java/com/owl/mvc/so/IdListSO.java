package com.owl.mvc.so;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/8.
 */
public class IdListSO {
    private List<Long> idList;

    public IdListSO(List<Long> idList) {
        this.idList = idList;
    }

    public static IdListSO getInstance(List<Long> idList) {
        return new IdListSO(idList);
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}

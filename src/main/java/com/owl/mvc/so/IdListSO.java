package com.owl.mvc.so;

import java.util.List;

/**
 * 删除或禁用 使用ID集合
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/8.
 */
public class IdListSO {
    private List<Long> idList;

    private IdListSO(List<Long> idList) {
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

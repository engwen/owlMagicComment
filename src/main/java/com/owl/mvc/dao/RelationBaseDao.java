package com.owl.mvc.dao;

import com.owl.mvc.so.ModelListSO;

/**
 * 關係數據類型dao，本接口對外開發
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/10.
 */
public interface RelationBaseDao<T> {

    /**
     * 批量插入
     * @param modelListSO 内含汎型對象
     * @return int
     */
    int insertList(ModelListSO<T> modelListSO);

    /**
     * 批量刪除
     * @param modelListSO 内含汎型對象
     * @return int
     */
    int deleteList(ModelListSO<T> modelListSO);
}

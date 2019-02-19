package com.owl.mvc.service;

import com.owl.mvc.vo.MsgResultVO;

import java.util.List;

/**
 * 不推薦的使用方式，采取包内策略，详见CellBaseServiceAb类 {@link RelationBaseServiceAb}
 * 關係型數據基礎
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/01/25.
 */
interface RelationBaseService<T> {

    /**
     * 插入關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO insert(T model);

    /**
     * 批量插入
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    MsgResultVO insertList(List<T> modelList);

    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO delete(T model);

    /**
     * 批量刪除
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    MsgResultVO deleteList(List<T> modelList);
}

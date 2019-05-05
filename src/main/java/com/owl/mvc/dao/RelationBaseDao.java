package com.owl.mvc.dao;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.ModelSO;

import java.util.List;

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
     * 批量插入
     * @param relationDTO 内含一對多
     * @return int
     */
    int insertRelation(RelationDTO relationDTO);

    /**
     * 批量刪除或个别删除
     * @param modelSO 内含汎型對象
     * @return int
     */
    int delete(ModelSO<T> modelSO);

    /**
     * 批量刪除
     * @param modelListSO 内含汎型對象
     * @return int
     */
    int deleteList(ModelListSO<T> modelListSO);

    /**
     * 批量刪除
     * @param relationDTO 内含一對多
     * @return int
     */
    int deleteRelation(RelationDTO relationDTO);

    /**
     * 查詢是否存在
     * @param modelSO 内含汎型對象
     * @return list
     */
    List<T> selectBySelective(ModelSO<T> modelSO);

}

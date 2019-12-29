package com.owl.mvc.dao;

import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.IdSO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;

import java.util.List;

/**
 * 底层对基礎dao进行统一，本接口对外开放
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/08/22.
 */
public interface CellBaseDao<T, ID> {

    /**
     * 直接插入
     * @param model 泛型对象
     * @return int
     */
    int insert(T model);

    /**
     * 批量插入
     * @param modelListSO 泛型对象集合List
     * @return int
     */
    int insertList(ModelListSO<T> modelListSO);


    /**
     * 物理 批量刪除
     * @param idListSO 内含id集合
     * @return int
     */
    int deleteByPrimaryKeyListRe(IdListSO<ID> idListSO);

    /**
     * 物理 刪除
     * @param model 泛型对象
     * @return int
     */
    int deleteByPrimaryKeyRe(T model);


    /**
     * 依據指定的屬性進行更新
     * @param model 泛型对象
     * @return int
     */
    int updateByPrimaryKey(T model);


    /**
     * 依據 id 屬性獲取對象集合 准确查询
     * @param idSO id泛型
     * @return 泛型对象集合
     */
    T selectByPrimaryKey(IdSO<ID> idSO);

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    List<T> selectByPrimaryKeyList(IdListSO<ID> idListSO);

    /**
     * 依據屬性獲取對象集合 准确查询
     * @param selectLikeSO 泛型对象
     *                     Param("model")
     * @return 泛型对象集合
     */
    List<T> selectByExact(SelectLikeSO<T> selectLikeSO);

    /**
     * 依據屬性獲取對象集合 粗略查询
     * @param selectLikeSO 泛型对象
     *                     Param("model")
     * @return 泛型对象集合
     */
    List<T> selectByLike(SelectLikeSO<T> selectLikeSO);


    /**
     * 依據指定的屬性統計數據條數
     * @param selectLikeSO 泛型对象
     * @return int
     */
    Integer countSumByCondition(SelectLikeSO<T> selectLikeSO);

    /**
     * 依據指定的屬性獲取指定的集合
     * @param selectLikeSO Param("upLimit")
     *                     Param("rows")
     *                     Param("model")
     * @return 泛型对象集合
     */
    List<T> listByCondition(SelectLikeSO<T> selectLikeSO);









/*-------------------------------      以下提供但是需要自己实现      ---------------------------------*/

    /**
     * 批量操作 禁用或啓用
     * @param banListDTO 對象
     *                   param idList 對象ID
     *                   param status 對象狀態
     * @return int
     */
    int banOrLeave(BanListDTO<ID> banListDTO);

    /**
     * 批量刪除
     * @param idListSO 内含id集合
     * @return int
     */
    int deleteByIdList(IdListSO<ID> idListSO);

    /**
     * 刪除
     * @param model 泛型对象
     * @return int
     */
    int deleteBySelective(T model);

}

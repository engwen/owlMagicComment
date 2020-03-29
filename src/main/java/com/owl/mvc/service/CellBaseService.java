package com.owl.mvc.service;

import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.IdSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.List;

/**
 * 不推薦的使用方式，采取包内策略，详见CellBaseServiceAb类 {@link CellBaseServiceAb}
 * 大多數的默認接口
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/01/22.
 */
interface CellBaseService<T, ID> {
    /**
     * 創建
     * @param model 汎型對象
     * @return 汎型對象
     */
    MsgResultVO<T> create(T model);

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    MsgResultVO<?> createList(List<T> modelList);

    /**
     * 物理刪除
     * @param model 對象
     * @return 汎型對象
     */
    MsgResultVO deleteRe(T model);

    MsgResultVO deleteByIdRe(ID id);

    /**
     * 物理刪除
     * @param idList ID集合
     * @return 汎型對象
     */
    MsgResultVO deleteByIdListRe(List<ID> idList);

    MsgResultVO deleteByIdListRe(DeleteDTO<ID> deleteDTO);


    /**
     * 全部属性更新
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO<?> update(T model);

    /**
     * 增量属性更新
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO<?> updateByNotNull(T model);


    /**
     * 獲取詳情
     * @param id id
     * @return 汎型對象
     */
    MsgResultVO<T> detailsById(ID id);

    MsgResultVO<T> detailsById(IdSO<ID> idSO);

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    MsgResultVO<T> details(T model);


    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    PageVO<T> list(Boolean getAll, Integer requestPage, Integer rows, T model);

    PageVO<T> list(PageDTO<T> pageDTO);

    /**
     * 獲取所有的對象，添加 model 提供檢索功能,精确查询
     * @param model 检索条件
     * @return 對象集合
     */
    MsgResultVO<List<T>> listByExact(T model);


    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    MsgResultVO<List<T>> selectByIdList(IdListSO<ID> idListSO);

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    MsgResultVO<?> isExist(T model);

    /*------------------------------------------------------------------------*/

    /**
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    MsgResultVO delete(T model);

    MsgResultVO deleteById(ID id);
    /**
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    MsgResultVO deleteList(List<ID> idList);

    MsgResultVO deleteList(DeleteDTO<ID> deleteDTO);


    /**
     * 批量操作 禁用或啓用
     * @param id     對象ID
     * @param status 對象狀態，可以爲空
     * @return 基礎數據
     */
    MsgResultVO banOrLeave(ID id, Boolean status);

    MsgResultVO banOrLeave(BanDTO<ID> banDTO);


    /**
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param status 對象狀態
     * @return 基礎數據
     */
    MsgResultVO banOrLeaveList(List<ID> idList, Boolean status);

    MsgResultVO banOrLeaveList(BanListDTO<ID> banListDTO);

}

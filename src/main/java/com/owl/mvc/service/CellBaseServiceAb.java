package com.owl.mvc.service;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.utils.CellBaseServiceUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 不可實例化，繼承者需要自己實現其中的方法，支持自定義以及添加方法
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class CellBaseServiceAb<M extends CellBaseDao<T, ID>, T, ID> implements CellBaseService<T, ID> {

    @Autowired
    private M cellBaseDao;

    /**
     * 創建
     * @param model 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> create(T model) {
        return CellBaseServiceUtil.create(cellBaseDao, model);
    }

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<?> createList(List<T> modelList) {
        return CellBaseServiceUtil.createList(cellBaseDao, modelList);
    }

    /**
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        return CellBaseServiceUtil.delete(cellBaseDao, model);
    }

    /**
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<ID> idList) {
        return CellBaseServiceUtil.deleteList(cellBaseDao, idList);
    }

    @Override
    public MsgResultVO deleteList(DeleteDTO<ID> deleteDTO) {
        return CellBaseServiceUtil.deleteList(cellBaseDao, deleteDTO);
    }


    /**
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteRe(T model) {
        return CellBaseServiceUtil.deleteRe(cellBaseDao, model);
    }

    /**
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteListRe(List<ID> idList) {
        return CellBaseServiceUtil.deleteListRe(cellBaseDao, idList);
    }

    @Override
    public MsgResultVO deleteListRe(DeleteDTO<ID> deleteDTO) {
        return CellBaseServiceUtil.deleteListRe(cellBaseDao, deleteDTO);
    }
    /**
     * 禁用或啓用
     * @param banDTO 禁用對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(BanDTO<ID> banDTO) {
        return CellBaseServiceUtil.banOrLeave(cellBaseDao, banDTO);
    }

    /**
     * 禁用或啓用
     * @param id    對象ID
     * @param isBan 對象狀態，可以爲空
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(ID id, Boolean isBan) {
        return CellBaseServiceUtil.banOrLeave(cellBaseDao, id, isBan);
    }


    /**
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param isBan  對象狀態
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeaveList(List<ID> idList, Boolean isBan) {
        return CellBaseServiceUtil.banOrLeaveList(cellBaseDao, idList, isBan);
    }

    @Override
    public MsgResultVO banOrLeaveList(BanListDTO<ID> banListDTO) {
        return CellBaseServiceUtil.banOrLeaveList(cellBaseDao, banListDTO);
    }

    /**
     * 更新 更新前需要查询，因此可能返回对象为父类型
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> update(T model) {
        return CellBaseServiceUtil.update(cellBaseDao, model);
    }

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        return CellBaseServiceUtil.details(cellBaseDao, model);
    }

    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param pageDTO 请求对象
     * @return 分頁對象
     */
    @Override
    public PageVO<T> list(PageDTO<T> pageDTO) {
        return CellBaseServiceUtil.list(cellBaseDao, pageDTO);
    }

    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    @Override
    public PageVO<T> list(Boolean getAll, Integer requestPage, Integer rows, T model) {
        return CellBaseServiceUtil.list(cellBaseDao, getAll, requestPage, rows, model);
    }

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    @Override
    public MsgResultVO<List<T>> selectByIdList(IdListSO idListSO) {
        return CellBaseServiceUtil.selectByIdList(cellBaseDao, idListSO);
    }

    /**
     * 獲取所有的對象
     * @return 對象集合
     */
    @Override
    public MsgResultVO<List<T>> getAll(T model) {
        return CellBaseServiceUtil.getAll(cellBaseDao, model);
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        return CellBaseServiceUtil.isExist(cellBaseDao, model);
    }
}

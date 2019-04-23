package com.owl.mvc.service;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.mvc.utils.CellBaseServiceUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 不可實例化，繼承者需要自己實現其中的方法，支持自定義以及添加方法
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class CellBaseServiceAb<T> implements CellBaseService<T> {
    private CellBaseDao<T> cellBaseDao;

    public void setCellBaseDao(CellBaseDao<T> cellBaseDao) {
        this.cellBaseDao = cellBaseDao;
    }

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
    public MsgResultVO deleteList(List<Long> idList) {
        return CellBaseServiceUtil.deleteList(cellBaseDao, idList);
    }

    @Override
    public MsgResultVO deleteList(DeleteDTO deleteDTO) {
        return CellBaseServiceUtil.deleteList(cellBaseDao, deleteDTO);
    }

    /**
     * 禁用或啓用
     * @param banDTO 禁用對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(BanDTO banDTO) {
        return CellBaseServiceUtil.banOrLeave(cellBaseDao, banDTO);
    }

    /**
     * 禁用或啓用
     * @param id    對象ID
     * @param isBan 對象狀態，可以爲空
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(Long id, Boolean isBan) {
        return CellBaseServiceUtil.banOrLeave(cellBaseDao, id, isBan);
    }


    /**
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param isBan  對象狀態
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeaveList(List<Long> idList, Boolean isBan) {
        return CellBaseServiceUtil.banOrLeaveList(cellBaseDao, idList, isBan);
    }

    @Override
    public MsgResultVO banOrLeaveList(BanListDTO banListDTO) {
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
    public MsgResultVO<PageVO<T>> list(PageDTO<T> pageDTO) {
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
    public MsgResultVO<PageVO<T>> list(Boolean getAll, Integer requestPage, Integer rows, T model) {
        return CellBaseServiceUtil.list(cellBaseDao, getAll, requestPage, rows, model);
    }


    /**
     * 獲取所有的對象
     * @return 對象集合
     */
    @Override
    public MsgResultVO<List<T>> listAll(T model) {
        return CellBaseServiceUtil.listAll(cellBaseDao, model);
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

package com.owl.mvc.service;

import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.List;
import java.util.logging.Logger;

/**
 * 不可實例化，繼承者需要自己實現其中的方法，支持自定義以及添加方法
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class CellBaseServiceAb<T> implements CellBaseService<T> {
    private static Logger logger = Logger.getLogger(CellBaseServiceAb.class.getName());

    private static void loggerInfo() {
        logger.warning("此方法没有被重写实现，默认的原始输出，将不会产生任何影响");
    }

    /**
     * 創建
     * @param model 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> create(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO createList(List<T> modelList) {
        loggerInfo();
        return null;
    }

    /**
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<Long> idList) {
        loggerInfo();
        return null;
    }

    /**
     * 批量操作 禁用或啓用
     * @param id     對象ID
     * @param status 對象狀態，可以爲空
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(Long id, Boolean status) {
        loggerInfo();
        return null;
    }

    @Override
    public MsgResultVO banOrLeave(BanDTO banDTO) {
        return null;
    }

    /**
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param status 對象狀態
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeaveList(List<Long> idList, Boolean status) {
        loggerInfo();
        return null;
    }

    @Override
    public MsgResultVO banOrLeaveList(BanListDTO banListDTO) {
        return null;
    }

    /**
     * 更新 更新前需要查询，因此可能返回对象为父类型
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO update(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        loggerInfo();
        return null;
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
        loggerInfo();
        return null;
    }

    @Override
    public MsgResultVO<PageVO<T>> list(PageDTO<T> pageDTO) {
        return null;
    }

    /**
     * 獲取所有的對象
     * @return 對象集合
     */
    @Override
    public MsgResultVO<List<T>> listAll(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO isExist(T model) {
        loggerInfo();
        return null;
    }
}

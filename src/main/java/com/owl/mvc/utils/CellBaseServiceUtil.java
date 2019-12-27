package com.owl.mvc.utils;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 本類適用于常見的方法，提供基礎解決方案，繼承service類之後，可在注入dao后使用本工具類快速完成基礎功能代碼
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/07.
 */
public abstract class CellBaseServiceUtil {

    /*
     * 創建
     * @param cellBaseDao cellBaseDao
     * @param model 汎型對象
     * @param <T> 汎型
     * @return 汎型對象
     */
    public static <T, ID> MsgResultVO<T> create(CellBaseDao<T, ID> cellBaseDao, T model) {
        cellBaseDao.insertSelective(model);
        return MsgResultVO.getInstanceSuccess(model);
    }

    /*
     * 創建
     * @param cellBaseDao cellBaseDao
     * @param model 汎型對象
     * @param <T> 汎型
     * @return 汎型對象
     */
    public static <T, ID> MsgResultVO<T> createByCheck(CellBaseDao<T, ID> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        if (isExist(cellBaseDao, model).getResult()) {
            resultVO.errorResult(MsgConstant.REQUEST_IS_EXITS);
        } else {
            cellBaseDao.insertSelective(model);
            resultVO.successResult(model);
        }
        return resultVO;
    }

    /*
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    public static <T, ID> MsgResultVO createList(CellBaseDao<T, ID> cellBaseDao, List<T> modelList) {
        cellBaseDao.insertList(ModelListSO.getInstance(modelList));
        return MsgResultVO.getInstanceSuccess();
    }


    /*
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO delete(CellBaseDao<T, ID> cellBaseDao, T model) {
        cellBaseDao.deleteBySelective(model);
        return MsgResultVO.getInstanceSuccess();
    }

    /*
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteList(CellBaseDao<T, ID> cellBaseDao, List<ID> idList) {
        IdListSO<ID> idListSO = new IdListSO<>(idList);
        cellBaseDao.deleteByIdList(idListSO);
        return MsgResultVO.getInstanceSuccess();
    }

    /*
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param deleteDTO ID集合
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteList(CellBaseDao<T, ID> cellBaseDao, DeleteDTO<ID> deleteDTO) {
        return deleteList(cellBaseDao, deleteDTO.getIdList());
    }


    /*
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteRe(CellBaseDao<T, ID> cellBaseDao, T model) {
        cellBaseDao.deleteBySelectiveRe(model);
        return MsgResultVO.getInstanceSuccess();
    }

    /*
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteListRe(CellBaseDao<T, ID> cellBaseDao, List<ID> idList) {
        IdListSO<ID> idListSO = new IdListSO<>(idList);
        cellBaseDao.deleteByIdListRe(idListSO);
        return MsgResultVO.getInstanceSuccess();
    }

    /*
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param deleteDTO ID集合
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteListRe(CellBaseDao<T, ID> cellBaseDao, DeleteDTO<ID> deleteDTO) {
        return deleteListRe(cellBaseDao, deleteDTO.getIdList());
    }


    /*
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO deleteByCheck(CellBaseDao<T, ID> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        if (isExist(cellBaseDao, model).getResult()) {
            cellBaseDao.deleteBySelective(model);
            resultVO.successResult();
        } else {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return resultVO;
    }


    public static <T, ID> MsgResultVO banOrLeave(CellBaseDao<T, ID> cellBaseDao, BanDTO<ID> banDTO) {
        return banOrLeave(cellBaseDao, banDTO.getId(), banDTO.getIsBan());
    }

    /*
     * 批量操作 禁用或啓用
     * @param id     對象ID
     * @param status 對象狀態，可以爲空
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO banOrLeave(CellBaseDao<T, ID> cellBaseDao, ID id, Boolean isBan) {
        List<ID> ids = new ArrayList<>();
        ids.add(id);
        return banOrLeaveList(cellBaseDao, ids, isBan);
    }


    /*
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param status 對象狀態
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO banOrLeaveList(CellBaseDao<T, ID> cellBaseDao, List<ID> idList, Boolean isBan) {
        BanListDTO<ID> banListDTO = new BanListDTO<>(idList, isBan);
        return banOrLeaveList(cellBaseDao, banListDTO);
    }

    public static <T, ID> MsgResultVO banOrLeaveList(CellBaseDao<T, ID> cellBaseDao, BanListDTO<ID> banListDTO) {
        cellBaseDao.banOrLeave(banListDTO);
        return MsgResultVO.getInstanceSuccess();
    }

    /*
     * 更新 更新前需要查询，因此可能返回对象为父类型
     * @param model 汎型對象
     * @return 基礎數據
     */
    public static <T, ID> MsgResultVO<T> update(CellBaseDao<T, ID> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        if (isExist(cellBaseDao, model).getResult()) {
            cellBaseDao.updateBySelective(model);
            resultVO.successResult();
        } else {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return resultVO;
    }

    /*
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    public static <T, ID> MsgResultVO<T> details(CellBaseDao<T, ID> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        List<T> temp = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        if (null == temp || temp.size() == 0) {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        } else if (temp.size() == 1) {
            resultVO.successResult(temp.get(0));
        } else {
            System.out.println("there are list with details back, but you just want one");
            resultVO.errorResult(MsgConstant.REQUEST_BACK_ARE_LIST);
        }
        return resultVO;
    }


    public static <T, ID> PageVO<T> list(CellBaseDao<T, ID> cellBaseDao, PageDTO<T> pageDTO) {
        return list(cellBaseDao, pageDTO.getGetAll(), pageDTO.getRequestPage(), pageDTO.getRows(), pageDTO.getModel());
    }

    /*
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    public static <T, ID> PageVO<T> list(CellBaseDao<T, ID> cellBaseDao, Boolean getAll, Integer requestPage, Integer rows, T model) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.initPageVO(cellBaseDao.countSumByCondition(SelectLikeSO.getInstance(model)), requestPage, rows, getAll);
        pageVO.setResultData(cellBaseDao.listByCondition(SelectLikeSO.getInstance(model, pageVO.getUpLimit(), pageVO.getRows())));
        return pageVO.successResult(MsgConstant.REQUEST_SUCCESS);
    }

    /*
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    public static <T, ID> MsgResultVO<List<T>> selectByIdList(CellBaseDao<T, ID> cellBaseDao, IdListSO idListSO) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByIdList(idListSO));
    }

    /*
     * 獲取所有的對象
     * @param model 汎型對象檢索條件
     * @return 對象集合
     */
    public static <T, ID> MsgResultVO<List<T>> getAll(CellBaseDao<T, ID> cellBaseDao, T model) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByLike(SelectLikeSO.getInstance(model)));
    }

    /*
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    public static <T, ID> MsgResultVO isExist(CellBaseDao<T, ID> cellBaseDao, T model) {
        MsgResultVO resultVO = new MsgResultVO();
        List<T> list = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        if (null != list && list.size() > 0) {
            resultVO.successResult(MsgConstant.REQUEST_IS_EXITS);
        } else {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return resultVO;
    }
}

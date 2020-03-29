package com.owl.mvc.service;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.*;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        cellBaseDao.insert(model);
        return MsgResultVO.getInstanceSuccess(model);
    }

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<?> createList(List<T> modelList) {
        cellBaseDao.insertList(ModelListSO.getInstance(modelList));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 物理删除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteRe(T model) {
        cellBaseDao.deleteBySelectiveRe(ModelSO.getInstance(model));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 物理删除
     * @param id 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteByIdRe(ID id) {
        cellBaseDao.deleteByPrimaryKeyRe(new IdSO<>(id));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 批量物理删除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteByIdListRe(List<ID> idList) {
        IdListSO<ID> idListSO = new IdListSO<>(idList);
        cellBaseDao.deleteByPrimaryKeyListRe(idListSO);
        return MsgResultVO.getInstanceSuccess();
    }

    @Override
    public MsgResultVO deleteByIdListRe(DeleteDTO<ID> deleteDTO) {
        return deleteByIdListRe(deleteDTO.getIdList());
    }


    /**
     * 更新 更新前需要查询
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> update(T model) {
        cellBaseDao.updateByPrimaryKey(model);
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 更新 更新前需要查询
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> updateByNotNull(T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        cellBaseDao.updateByPrimaryKeySelective(model);
        resultVO.successResult();
        return resultVO;
    }


    /**
     * 獲取詳情
     * @param id 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> detailsById(ID id) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByPrimaryKey(new IdSO<>(id)));
    }

    @Override
    public MsgResultVO<T> detailsById(IdSO<ID> idSO) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByPrimaryKey(idSO));
    }
    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> details(T model) {
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

    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param pageDTO 请求对象
     * @return 分頁對象
     */
    @Override
    public PageVO<T> list(PageDTO<T> pageDTO) {
        return list(pageDTO.getGetAll(), pageDTO.getRequestPage(), pageDTO.getRows(), pageDTO.getModel());
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
        PageVO<T> pageVO = new PageVO<>();
        pageVO.initPageVO(cellBaseDao.countSumByCondition(SelectLikeSO.getInstance(model)), requestPage, rows, getAll);
        pageVO.setResultData(cellBaseDao.listByCondition(SelectLikeSO.getInstance(model, pageVO.getUpLimit(), pageVO.getRows())));
        return pageVO.successResult(MsgConstant.REQUEST_SUCCESS);
    }

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    @Override
    public MsgResultVO<List<T>> selectByIdList(IdListSO idListSO) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByPrimaryKeyList(idListSO));
    }

    /**
     * 精确查询，獲取所有符合条件的對象
     * @return 對象集合
     */
    @Override
    public MsgResultVO<List<T>> listByExact(T model) {
        return MsgResultVO.getInstanceSuccess(cellBaseDao.selectByExact(SelectLikeSO.getInstance(model)));
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        MsgResultVO resultVO = new MsgResultVO();
        List<T> list = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        if (null != list && list.size() > 0) {
            resultVO.successResult(MsgConstant.REQUEST_IS_EXITS);
        } else {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return resultVO;
    }







    /*-----------------------------------------------------------------------------------*/


    /**
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        cellBaseDao.deleteBySelective(ModelSO.getInstance(model));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 物理删除
     * @param id 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteById(ID id) {
        cellBaseDao.deleteByPrimaryKey(new IdSO<>(id));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<ID> idList) {
        IdListSO<ID> idListSO = new IdListSO<>(idList);
        cellBaseDao.deleteByPrimaryKeyList(idListSO);
        return MsgResultVO.getInstanceSuccess();
    }

    @Override
    public MsgResultVO deleteList(DeleteDTO<ID> deleteDTO) {
        return deleteList(deleteDTO.getIdList());
    }

    /**
     * 禁用或啓用
     * @param banDTO 禁用對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(BanDTO<ID> banDTO) {
        return banOrLeave(banDTO.getId(), banDTO.getIsBan());
    }

    /**
     * 禁用或啓用
     * @param id    對象ID
     * @param isBan 對象狀態，可以爲空
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeave(ID id, Boolean isBan) {
        List<ID> ids = new ArrayList<>();
        ids.add(id);
        return banOrLeaveList(ids, isBan);
    }

    /**
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param isBan  對象狀態
     * @return 基礎數據
     */
    @Override
    public MsgResultVO banOrLeaveList(List<ID> idList, Boolean isBan) {
        BanListDTO<ID> banListDTO = new BanListDTO<>(idList, isBan);
        return banOrLeaveList(banListDTO);
    }

    @Override
    public MsgResultVO banOrLeaveList(BanListDTO<ID> banListDTO) {
        cellBaseDao.banOrLeave(banListDTO);
        return MsgResultVO.getInstanceSuccess();
    }
}

package com.owl.mvc.service;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.vo.MsgResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class RelationBaseServiceAb<T> implements RelationBaseService<T> {
    private RelationBaseDao<T> relationBaseDao;

    public void setRelationBaseDao(RelationBaseDao<T> relationBaseDao) {
        this.relationBaseDao = relationBaseDao;
    }

    /**
     * 插入關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insert(T model) {
        List<T> modelList = new ArrayList<>();
        modelList.add(model);
        return this.insertList(modelList);
    }

    /**
     * 批量插入
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insertList(List<T> modelList) {
        MsgResultVO resultVO = new MsgResultVO();
        relationBaseDao.insertList(ModelListSO.getInstance(modelList));
        return resultVO.successResult();
    }

    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        List<T> modelList = new ArrayList<>();
        modelList.add(model);
        return this.deleteList(modelList);
    }

    /**
     * 批量刪除
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<T> modelList) {
        MsgResultVO resultVO = new MsgResultVO();
        relationBaseDao.deleteList(ModelListSO.getInstance(modelList));
        return resultVO.successResult();
    }
}

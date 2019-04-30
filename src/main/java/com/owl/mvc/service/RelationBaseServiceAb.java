package com.owl.mvc.service;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.utils.RelationBaseServiceUtil;
import com.owl.mvc.vo.MsgResultVO;

import javax.transaction.Transactional;
import java.util.List;

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
        return RelationBaseServiceUtil.insert(relationBaseDao, model);
    }

    /**
     * 批量插入
     * @param relationDTO id idList
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insertRelation(RelationDTO relationDTO) {
        return RelationBaseServiceUtil.insertRelation(relationBaseDao, relationDTO);
    }

    /**
     * 批量插入
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insertList(List<T> modelList) {
        return RelationBaseServiceUtil.insertList(relationBaseDao, modelList);
    }

    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        return RelationBaseServiceUtil.delete(relationBaseDao, model);
    }

    /**
     * 批量刪除
     * @param relationDTO id idList
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteRelation(RelationDTO relationDTO) {
        return RelationBaseServiceUtil.deleteRelation(relationBaseDao, relationDTO);
    }

    /**
     * 批量刪除
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<T> modelList) {
        return RelationBaseServiceUtil.deleteList(relationBaseDao, modelList);
    }
}

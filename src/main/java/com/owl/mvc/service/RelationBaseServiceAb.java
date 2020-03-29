package com.owl.mvc.service;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class RelationBaseServiceAb<M extends RelationBaseDao<T, MainID, FollowerID>, T, MainID, FollowerID>
        implements RelationBaseService<T, MainID, FollowerID> {
    @Autowired
    private M relationBaseDao;

    /**
     * 插入關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insert(T model) {
        List<T> list = relationBaseDao.selectBySelective(ModelSO.getInstance(model));
        if (list.size() > 0) {
            return MsgResultVO.getInstanceSuccess();
        } else {
            List<T> temp = new ArrayList<>();
            temp.add(model);
            return insertList(temp);
        }
    }

    /**
     * 批量插入
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insertList(List<T> modelList) {
        relationBaseDao.insertList(ModelListSO.getInstance(modelList));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        relationBaseDao.delete(ModelSO.getInstance(model));
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 批量刪除
     * @param relationDTO id idList
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteRelation(RelationDTO<MainID, FollowerID> relationDTO) {
        relationBaseDao.deleteRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 查詢
     * @param model d idList
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<List<T>> list(T model) {
        return MsgResultVO.getInstanceSuccess(relationBaseDao.selectBySelective(ModelSO.getInstance(model)));
    }
}

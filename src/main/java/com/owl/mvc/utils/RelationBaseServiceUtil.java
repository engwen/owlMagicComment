package com.owl.mvc.utils;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.vo.MsgResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 類適用于常見的方法，提供基礎解決方案，繼承service類之後，可在注入dao后使用本工具類快速完成基礎功能代碼
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/10.
 */
public abstract class RelationBaseServiceUtil {
    private static Logger logger = Logger.getLogger(CellBaseServiceUtil.class.getName());


    public static <T> MsgResultVO insert(RelationBaseDao<T> relationBaseDao, T model) {
        List<T> temp = new ArrayList<>();
        temp.add(model);
        return insertList(relationBaseDao, temp);
    }

    public static <T> MsgResultVO insertList(RelationBaseDao<T> relationBaseDao, List<T> modelList) {
        MsgResultVO resultVO = new MsgResultVO();
        try {
            relationBaseDao.insertList(ModelListSO.getInstance(modelList));
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with insertList,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    public static <T> MsgResultVO delete(RelationBaseDao<T> relationBaseDao, T model) {
        List<T> temp = new ArrayList<>();
        temp.add(model);
        return deleteList(relationBaseDao, temp);
    }

    public static <T> MsgResultVO deleteList(RelationBaseDao<T> relationBaseDao, List<T> modelList) {
        MsgResultVO resultVO = new MsgResultVO();
        try {
            relationBaseDao.deleteList(ModelListSO.getInstance(modelList));
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with deleteList,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }
}

package com.owl.mvc.utils;

import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.vo.MsgResultVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 類適用于常見的方法，提供基礎解決方案，繼承service類之後，可在注入dao后使用本工具類快速完成基礎功能代碼
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/10.
 */
public abstract class RelationBaseControllerUtil {


    public static <T> MsgResultVO insertList(RelationBaseServiceAb<T> relationBaseServiceAb, List<T> modelList) {
        MsgResultVO resultVO;
        try {
            resultVO = relationBaseServiceAb.insertList(modelList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    public static <T> MsgResultVO deleteList(RelationBaseServiceAb<T> relationBaseServiceAb, List<T> modelList) {
        MsgResultVO resultVO;
        try {
            resultVO = relationBaseServiceAb.deleteList(modelList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }
}

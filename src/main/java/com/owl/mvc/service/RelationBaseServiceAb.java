package com.owl.mvc.service;

import com.owl.mvc.vo.MsgResultVO;

import java.util.List;
import java.util.logging.Logger;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class RelationBaseServiceAb<T> implements RelationBaseService<T> {
    private static Logger logger = Logger.getLogger(RelationBaseServiceAb.class.getName());

    private static void loggerInfo() {
        logger.warning("此方法没有被重写实现，默认的原始输出，将不会产生任何影响");
    }

    /**
     * 插入關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insert(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 批量插入
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO insertList(List<T> modelList) {
        loggerInfo();
        return null;
    }

    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO delete(T model) {
        loggerInfo();
        return null;
    }

    /**
     * 批量刪除
     * @param modelList 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO deleteList(List<T> modelList) {
        loggerInfo();
        return null;
    }
}

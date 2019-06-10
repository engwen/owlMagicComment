package com.owl.mvc.controller;

import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.logging.Logger;

/**
 * 本类可用于Controller的集成，定义了常用的部分功能，继承本类后实现即可
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/07/16.
 */
@Controller
public abstract class CellBaseControllerAb<T> implements CellBaseController<T> {
    private static Logger logger = Logger.getLogger(CellBaseControllerAb.class.getName());

    private MsgResultVO defaultBack() {
        logger.info("默认的原始输出，将不会产生任何影响");
        MsgResultVO result = new MsgResultVO();
        return result.errorResult(MsgConstant.REQUEST_METHOD_NOT_EXITS);
    }

    /**
     * 创建
     * @param model 将要被创建的对象
     * @return 创建后的对象返回数据
     */
    @Override
    public MsgResultVO<T> create(T model) {
        return defaultBack();
    }

    /**
     * 批量创建
     * @param list 待创建对象集合
     * @return 结果
     */
    @Override
    public MsgResultVO<?> createList(List<T> list) {
        return defaultBack();
    }


    /**
     * 删除功能
     * @param model 待删除的对象
     * @return 结果
     */
    @Override
    public MsgResultVO delete(T model) {
        return defaultBack();
    }


    @Override
    public MsgResultVO deleteList(DeleteDTO deleteDTO) {
        return defaultBack();
    }

    @Override
    public MsgResultVO banOrLeave(BanDTO banDTO) {
        return defaultBack();
    }


    @Override
    public MsgResultVO banOrLeaveList(BanListDTO banListDTO) {
        return defaultBack();
    }

    /**
     * 更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> update(T model) {
        return defaultBack();
    }

    /**
     * 获取详情
     * @param model 获取详情的对象唯一属性
     * @return 结果对象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        return defaultBack();
    }


    @Override
    public MsgResultVO<PageVO<T>> list(PageDTO<T> pageDTO) {
        return defaultBack();
    }

    /**
     * 获取所有对象
     * @param model 检索条件
     * @return 结果集合
     */
    @Override
    public MsgResultVO<List<T>> getAll(T model) {
        return defaultBack();
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        return defaultBack();
    }
}

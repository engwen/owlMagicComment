package com.owl.mvc.controller;

import com.owl.comment.utils.AsLogUtil;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 本类可用于Controller的集成，定义了常用的部分功能，继承本类后实现即可
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/07/16.
 */
@RestController
@SuppressWarnings("unchecked")
public abstract class CellBaseControllerAb<M extends CellBaseServiceAb, T, ID> implements CellBaseController<T, ID> {

    @Autowired
    private M cellBaseServiceAb;

    private static void defaultBack() {
        AsLogUtil.info("The default raw output will just do we default want, if you want do other ,please override");
    }

    /**
     * 创建
     * @param model 将要被创建的对象
     * @return 创建后的对象返回数据
     */
    @Override
    public MsgResultVO<T> create(T model) {
        defaultBack();
        return cellBaseServiceAb.create(model);
    }

    /**
     * 批量创建
     * @param list 待创建对象集合
     * @return 结果
     */
    @Override
    public MsgResultVO<?> createList(List<T> list) {
        defaultBack();
        return cellBaseServiceAb.createList(list);
    }


    /**
     * 删除功能
     * @param model 待删除的对象
     * @return 结果
     */
    @Override
    public MsgResultVO delete(T model) {
        defaultBack();
        return cellBaseServiceAb.delete(model);
    }

    /**
     * 删除功能
     * @param id 待删除的对象id
     * @return 结果
     */
    @Override
    public MsgResultVO deleteById(ID id) {
        defaultBack();
        return cellBaseServiceAb.deleteById(id);
    }

    /**
     * 批量删除
     * @param deleteDTO 删除对象DTO
     * @return 结果
     */
    @Override
    public MsgResultVO deleteList(DeleteDTO<ID> deleteDTO) {
        defaultBack();
        return cellBaseServiceAb.deleteList(deleteDTO);
    }

    /**
     * 删除功能
     * @param model 待删除的对象
     * @return 结果
     */
    @Override
    public MsgResultVO deleteRe(T model) {
        defaultBack();
        return cellBaseServiceAb.deleteRe(model);
    }

    /**
     * 批量删除
     * @param deleteDTO 删除对象DTO
     * @return 结果
     */
    @Override
    public MsgResultVO deleteListRe(DeleteDTO<ID> deleteDTO) {
        defaultBack();
        return cellBaseServiceAb.deleteByIdListRe(deleteDTO);
    }

    /**
     * 批量操作 禁用或啓用
     * @param banDTO 禁用对象
     * @return int
     */
    @Override
    public MsgResultVO banOrLeave(BanDTO<ID> banDTO) {
        defaultBack();
        return cellBaseServiceAb.banOrLeave(banDTO);
    }

    /**
     * 批量操作 禁用或啓用
     * @param banListDTO 禁用对象集合
     * @return int
     */
    @Override
    public MsgResultVO banOrLeaveList(BanListDTO<ID> banListDTO) {
        defaultBack();
        return cellBaseServiceAb.banOrLeaveList(banListDTO);
    }

    /**
     * 全量更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> update(T model) {
        defaultBack();
        return cellBaseServiceAb.update(model);
    }

    /**
     * 增量更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> updateByNotNull(T model) {
        defaultBack();
        return cellBaseServiceAb.updateByNotNull(model);
    }


    /**
     * 获取详情
     * @param model 获取详情的对象唯一属性
     * @return 结果对象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        defaultBack();
        return cellBaseServiceAb.details(model);
    }

    /**
     * 获取分页集合
     * @param pageDTO 请求分页对象
     * @return 分页集合
     */
    @Override
    public PageVO<T> list(PageDTO<T> pageDTO) {
        defaultBack();
        return cellBaseServiceAb.list(pageDTO);
    }

    /**
     * 获取所有对象
     * @param model 检索条件
     * @return 结果集合
     */
    @Override
    public MsgResultVO<List<T>> list(T model) {
        defaultBack();
        return cellBaseServiceAb.listByExact(model);
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        defaultBack();
        return cellBaseServiceAb.isExist(model);
    }
}

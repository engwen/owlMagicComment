package com.owl.mvc.utils;

import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.List;

/**
 * 本類主要用於開啓事務回滾的controller
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/23.
 */
public abstract class CellBaseControllerUtil {

    /*
     * 创建
     * @param cellBaseServiceAb service对象
     * @param model             将要被创建的对象
     * @return 创建后的对象返回数据
     */
    public static <T> MsgResultVO<T> create(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        MsgResultVO<T> resultVO;
        try {
            resultVO = cellBaseServiceAb.create(model);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量创建
     * @param cellBaseServiceAb service对象
     * @param list              待创建对象集合
     * @return 结果
     */
    public static <T> MsgResultVO createList(CellBaseServiceAb<T> cellBaseServiceAb, List<T> list) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.createList(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }


    /*
     * 删除功能
     * @param cellBaseServiceAb service对象
     * @param model             待删除的对象
     * @return 结果
     */
    public static <T> MsgResultVO delete(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.delete(model);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量删除
     * @param cellBaseServiceAb service对象
     * @param idList            待删除的id集合
     * @return 结果
     */
    public static <T> MsgResultVO deleteList(CellBaseServiceAb<T> cellBaseServiceAb, DeleteDTO deleteDTO) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.deleteList(deleteDTO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量操作 禁用或啓用
     * @param cellBaseServiceAb service对象
     * @param id                對象ID
     * @param status            對象狀態，可以爲空
     * @return int
     */
    public static <T> MsgResultVO banOrLeave(CellBaseServiceAb<T> cellBaseServiceAb, BanDTO banDTO) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.banOrLeave(banDTO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    public static <T> MsgResultVO banOrLeaveList(CellBaseServiceAb<T> cellBaseServiceAb, BanListDTO banListDTO) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.banOrLeaveList(banListDTO);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    /*
     * 更新
     * @param cellBaseServiceAb service对象
     * @param model             将要被更新的对象
     * @return 结果
     */
    public static <T> MsgResultVO update(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        MsgResultVO resultVO;
        try {
            resultVO = cellBaseServiceAb.update(model);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new MsgResultVO<>();
            resultVO.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
        }
        return resultVO;
    }

    /*
     * 获取详情
     * @param cellBaseServiceAb service对象
     * @param model             获取详情的对象唯一属性
     * @return 结果对象
     */
    public static <T> MsgResultVO<T> details(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        return cellBaseServiceAb.details(model);
    }

    /*
     * 获取分页集合
     * @param cellBaseServiceAb service对象
     * @param requestPage       请求页数
     * @param rows              请求显示条数
     * @param model             检索对象属性
     * @return 分页集合
     */
    public static <T> MsgResultVO<PageVO<T>> list(CellBaseServiceAb<T> cellBaseServiceAb, PageDTO<T> pageDTO) {
        return cellBaseServiceAb.list(pageDTO);
    }

    /*
     * 获取所有对象
     * @param cellBaseServiceAb service对象
     * @param model             检索条件
     * @return 结果集合
     */
    public static <T> MsgResultVO<List<T>> listAll(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        return cellBaseServiceAb.listAll(model);
    }

    /*
     * 檢查数据是否存在
     * @param cellBaseServiceAb service对象
     * @param model             检索条件
     * @return Boolean
     */
    public static <T> MsgResultVO isExist(CellBaseServiceAb<T> cellBaseServiceAb, T model) {
        return cellBaseServiceAb.isExist(model);
    }
}

package com.owl.mvc.utils;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 本類適用于常見的方法，提供基礎解決方案，繼承service類之後，可在注入dao后使用本工具類快速完成基礎功能代碼
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/07.
 */
public abstract class CellBaseServiceUtil {
    private static Logger logger = Logger.getLogger(CellBaseServiceUtil.class.getName());

    /*
     * 創建
     * @param cellBaseDao cellBaseDao
     * @param model 汎型對象
     * @param <T> 汎型
     * @return 汎型對象
     */
    public static <T> MsgResultVO<T> create(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            cellBaseDao.insertSelective(model);
            resultVO.successResult(model);
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with create,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 創建
     * @param cellBaseDao cellBaseDao
     * @param model 汎型對象
     * @param <T> 汎型
     * @return 汎型對象
     */
    public static <T> MsgResultVO<T> createByCheck(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            if (isExist(cellBaseDao, model).getResult()) {
                resultVO.errorResult(MsgConstant.REQUEST_IS_EXITS);
            } else {
                cellBaseDao.insertSelective(model);
                resultVO.successResult(model);
            }
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with create,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    public static <T> MsgResultVO createList(CellBaseDao<T> cellBaseDao, List<T> modelList) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            cellBaseDao.insertList(ModelListSO.getInstance(modelList));
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with createList,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }


    /*
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    public static <T> MsgResultVO delete(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            cellBaseDao.deleteBySelective(model);
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with delete,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 刪除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    public static <T> MsgResultVO deleteByCheck(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            if (isExist(cellBaseDao, model).getResult()) {
                cellBaseDao.deleteBySelective(model);
                resultVO.successResult();
            } else {
                resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
            }
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with delete,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量刪除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    public static <T> MsgResultVO deleteList(CellBaseDao<T> cellBaseDao, List<Long> idList) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            cellBaseDao.deleteByIdList(IdListSO.getInstance(idList));
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with deleteList,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 批量操作 禁用或啓用
     * @param id     對象ID
     * @param status 對象狀態，可以爲空
     * @return 基礎數據
     */
    public static <T> MsgResultVO banOrLeave(CellBaseDao<T> cellBaseDao, Long id, Boolean isBan) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return banOrLeaveList(cellBaseDao, ids, isBan);
    }

    public static <T> MsgResultVO banOrLeave(CellBaseDao<T> cellBaseDao, BanDTO banDTO) {
        return banOrLeave(cellBaseDao, banDTO.getId(), banDTO.getIsBan());
    }

    /*
     * 批量操作 禁用或啓用
     * @param idList 對象ID
     * @param status 對象狀態
     * @return 基礎數據
     */
    public static <T> MsgResultVO banOrLeaveList(CellBaseDao<T> cellBaseDao, List<Long> idList, Boolean isBan) {
        BanListDTO banListDTO = new BanListDTO();
        banListDTO.setIdList(idList);
        banListDTO.setIsBan(isBan);
        cellBaseDao.banOrLeave(banListDTO);
        return banOrLeaveList(cellBaseDao, banListDTO);
    }

    public static <T> MsgResultVO banOrLeaveList(CellBaseDao<T> cellBaseDao, BanListDTO banListDTO) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            cellBaseDao.banOrLeave(banListDTO);
            resultVO.successResult();
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with banOrLeaveList,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 更新 更新前需要查询，因此可能返回对象为父类型
     * @param model 汎型對象
     * @return 基礎數據
     */
    public static <T> MsgResultVO<T> update(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            if (isExist(cellBaseDao, model).getResult()) {
                cellBaseDao.updateBySelective(model);
                resultVO.successResult();
            } else {
                resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
            }
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with update,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    public static <T> MsgResultVO<T> details(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        try {
            List<T> temp = cellBaseDao.selectBySelective(SelectLikeSO.getInstance(model));
            if (null != temp && temp.size() == 1) {
                resultVO.successResult(temp.get(0));
            } else {
                logger.info("there are list with details back, but you just want one");
                resultVO.errorResult(MsgConstant.REQUEST_BACK_ARE_LIST);
            }
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with details,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }


    /*
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    public static <T> MsgResultVO<PageVO<T>> list(CellBaseDao<T> cellBaseDao, Boolean getAll, Integer requestPage, Integer rows, T model) {
        MsgResultVO<PageVO<T>> resultVO = new MsgResultVO<>();
        try {
            PageVO<T> pageVO = new PageVO<>();
            pageVO.initPageVO(cellBaseDao.countSumByCondition(SelectLikeSO.getInstance(model)), requestPage, rows, getAll);
            pageVO.setObjectList(cellBaseDao.listByCondition(SelectLikeSO.getInstance(model, pageVO.getUpLimit(), pageVO.getRows())));
            resultVO.successResult(pageVO);
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with list,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    public static <T> MsgResultVO<PageVO<T>> list(CellBaseDao<T> cellBaseDao, PageDTO<T> pageDTO) {
        return list(cellBaseDao, pageDTO.getGetAll(), pageDTO.getRequestPage(), pageDTO.getRows(), pageDTO.getModel());
    }

    /*
     * 獲取所有的對象
     * @param model 汎型對象檢索條件
     * @return 對象集合
     */
    public static <T> MsgResultVO<List<T>> listAll(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO<List<T>> resultVO = new MsgResultVO<>();
        try {
            resultVO.successResult(cellBaseDao.selectBySelective(SelectLikeSO.getInstance(model)));
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with details,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }

    /*
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */

    public static <T> MsgResultVO isExist(CellBaseDao<T> cellBaseDao, T model) {
        MsgResultVO resultVO = new MsgResultVO();
        try {
            List<T> list = cellBaseDao.selectBySelective(SelectLikeSO.getInstance(model));
            if (null != list && list.size() > 0) {
                resultVO.successResult(MsgConstant.REQUEST_IS_EXITS);
            } else {
                resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
            }
        } catch (Exception e) {
            logger.info(String.format("there is a bad thing begin with isExist,information is %s", e));
            resultVO.errorResult(MsgConstant.REQUEST_DB_ERROR);
        }
        return resultVO;
    }
}

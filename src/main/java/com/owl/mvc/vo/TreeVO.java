package com.owl.mvc.vo;

import com.owl.magicUtil.model.ModelPrototype;

import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/06/15.
 */
public final class TreeVO<T> extends ModelPrototype {
    private Long id;
    private Long pid;

    private T treeObj;

    private List<TreeVO> treeVOList = new ArrayList<>();

    public TreeVO(Integer id, Integer pid, T t) {
        this.id = id.longValue();
        this.pid = pid.longValue();
        this.treeObj = t;
    }

    public TreeVO(Long id, Long pid, T t) {
        this.id = id;
        this.pid = pid;
        this.treeObj = t;
    }

    public T getTreeObj() {
        return treeObj;
    }

    public void setTreeObj(T treeObj) {
        this.treeObj = treeObj;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<TreeVO> getTreeVOList() {
        return treeVOList;
    }

    public void setTreeVOList(List<TreeVO> treeVOList) {
        this.treeVOList = treeVOList;
    }

    /**
     * 获取树
     * @param treeVOList 分樹的對象
     * @return List
     */
    public static List<TreeVO> getTree(List<TreeVO> treeVOList) {
        List<TreeVO> root = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (null == treeVO.pid || treeVO.pid == 0 || treeVO.pid < 0) {
                root.add(getTrees(treeVO, treeVOList));
            }
        });
        return root;
    }

    /**
     * 获取子叶
     * @param root       根
     * @param treeVOList 子葉
     * @return TreeVO
     */
    @SuppressWarnings("unchecked")
    private static TreeVO getTrees(TreeVO root, List<TreeVO> treeVOList) {
        treeVOList.forEach(treeVO -> {
            if (null != treeVO.pid && treeVO.pid.equals(root.id)) {
                root.treeVOList.add(getTrees(treeVO, treeVOList));
            }
        });
        return root;
    }


    /**
     * 获取对应的开始节点，并返回目标节点及以下的树id集合
     * @param aimID      目标节点
     * @param treeVOList 所有的集合
     * @return List
     */
    public static List<Long> getIdTree(Long aimID, List<TreeVO> treeVOList) {
        List<Long> result = new ArrayList<>();
        if (null != aimID) {
            treeVOList.forEach(treeVO -> {
                if ((aimID == 0 && treeVO.pid == 0) || aimID.equals(treeVO.id)) {
                    result.addAll(getIdTrees(treeVO, treeVOList));
                }
            });
        }
        //获取根
        return result;
    }

    /**
     * 获取节点以及以下
     * @param root       根
     * @param treeVOList 對象集合
     * @return List
     */
    private static List<Long> getIdTrees(TreeVO root, List<TreeVO> treeVOList) {
        List<Long> result = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (root.id.equals(treeVO.pid)) {
                result.addAll(getIdTrees(treeVO, treeVOList));
            }
        });
        result.add(root.id);
        return result;
    }
}

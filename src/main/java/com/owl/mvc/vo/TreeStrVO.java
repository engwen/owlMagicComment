package com.owl.mvc.vo;

import com.owl.magicUtil.model.ModelPrototype;

import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/06/15.
 */
public final class TreeStrVO<T> extends ModelPrototype {
    private String id;
    private String pid;

    private T treeObj;

    private List<TreeStrVO> treeVOList = new ArrayList<>();

    public TreeStrVO(String id, String pid, T t) {
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<TreeStrVO> getTreeVOList() {
        return treeVOList;
    }

    public void setTreeVOList(List<TreeStrVO> treeVOList) {
        this.treeVOList = treeVOList;
    }

    /**
     * 获取树
     * @param treeVOList 分樹的對象
     * @return List
     */
    public static List<TreeStrVO> getTree(List<TreeStrVO> treeVOList) {
        List<TreeStrVO> root = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (null == treeVO.pid || treeVO.pid.equals("")) {
                root.add(getTrees(treeVO, treeVOList));
            }
        });
        return root;
    }

    /**
     * 获取子叶
     * @param root       根
     * @param treeVOList 子葉
     * @return TreeStrVO
     */
    @SuppressWarnings("unchecked")
    private static TreeStrVO getTrees(TreeStrVO root, List<TreeStrVO> treeVOList) {
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
    public static List<String> getIdTree(String aimID, List<TreeStrVO> treeVOList) {
        List<String> result = new ArrayList<>();
        if (null != aimID) {
            treeVOList.forEach(treeVO -> {
                if ((aimID == "" && treeVO.pid == "") || aimID.equals(treeVO.id)) {
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
    private static List<String> getIdTrees(TreeStrVO root, List<TreeStrVO> treeVOList) {
        List<String> result = new ArrayList<>();
        treeVOList.forEach(treeVO -> {
            if (root.id.equals(treeVO.pid)) {
                result.addAll(getIdTrees(treeVO, treeVOList));
            }
        });
        result.add(root.id);
        return result;
    }
}

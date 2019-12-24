package com.owl.mvc.dto;

import java.util.List;

/**
 * 关系接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
public class RelationDTO<MainID, FollowerID> {
    /*
     * 主id
     * */
    private MainID id;
    /*
     * 副id集合
     * */
    private List<FollowerID> idList;

    public RelationDTO() {
    }

    public RelationDTO(MainID id, List<FollowerID> idList) {
        this.id = id;
        this.idList = idList;
    }

    public MainID getId() {
        return id;
    }

    public void setId(MainID id) {
        this.id = id;
    }

    public List<FollowerID> getIdList() {
        return idList;
    }

    public void setIdList(List<FollowerID> idList) {
        this.idList = idList;
    }
}

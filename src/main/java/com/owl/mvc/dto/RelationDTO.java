package com.owl.mvc.dto;

import java.util.List;

/**
 * 关系接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
public class RelationDTO {
    /*
    * 主id
    * */
    private Long id;
    /*
    * 副id集合
    * */
    private List<Long> idList;

    public RelationDTO() {
    }

    public RelationDTO(Long id, List<Long> idList) {
        this.id = id;
        this.idList = idList;
    }

    public static RelationDTO getInstance(Long id, List<Long> idList) {
        return new RelationDTO(id, idList);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}

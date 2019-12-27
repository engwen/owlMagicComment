package com.owl.mvc.dto;

import java.util.List;

/**
 * 删除接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class DeleteDTO<ID> {
    /*
    * id
    * */
    private ID id;
    /*
    * id集合
    * */
    private List<ID> idList;

    public DeleteDTO() {
    }

    private DeleteDTO(ID id) {
        this.id = id;
    }

    private DeleteDTO(List<ID> idList) {
        this.idList = idList;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void setIdList(List<ID> idList) {
        this.idList = idList;
    }
}

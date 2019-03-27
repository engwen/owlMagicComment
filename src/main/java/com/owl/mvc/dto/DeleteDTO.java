package com.owl.mvc.dto;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class DeleteDTO {
    private Long id;
    //待删除的id集合
    private List<Long> idList;

    public DeleteDTO() {
    }

    private DeleteDTO(Long id) {
        this.id = id;
    }

    private DeleteDTO(List<Long> idList) {
        this.idList = idList;
    }

    public static DeleteDTO getInstance(Long id) {
        return new DeleteDTO(id);
    }

    public static DeleteDTO getInstance(List<Long> idList) {
        return new DeleteDTO(idList);
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

package com.owl.mvc.dto;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class DeleteDTO {
    private Long id;
    private List<Long> idList;

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

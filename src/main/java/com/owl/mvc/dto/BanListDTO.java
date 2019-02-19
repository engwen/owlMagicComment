package com.owl.mvc.dto;

import java.util.List;

public class BanListDTO {
    private List<Long> idList;
    private Boolean isBan;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(Boolean ban) {
        this.isBan = ban;
    }

}

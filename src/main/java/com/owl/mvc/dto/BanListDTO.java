package com.owl.mvc.dto;

import java.util.List;

public class BanListDTO {
    private List<Long> idList;
    private Boolean isBan;

    public BanListDTO() {
    }

    private BanListDTO(List<Long> idList, Boolean isBan) {
        this.idList = idList;
        this.isBan = isBan;
    }

    public static BanListDTO getInstance(List<Long> idList, Boolean isBan) {
        return new BanListDTO(idList, isBan);
    }

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

package com.owl.mvc.dto;

import java.util.List;

/**
 * 禁用集合类接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class BanListDTO {
    /*
    * id集合
    * */
    private List<Long> idList;
    /*
    * 是否禁用
    * */
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

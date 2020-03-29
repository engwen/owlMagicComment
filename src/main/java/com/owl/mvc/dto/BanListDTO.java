package com.owl.mvc.dto;

import java.util.List;

/**
 * 禁用集合类接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class BanListDTO<ID> {
    /*
     * id集合
     * */
    private List<ID> idList;
    /*
     * 是否禁用
     * */
    private Boolean hasBan;

    public static <ID> BanListDTO getInstance(List<ID> idList, Boolean hasBan) {
        return new BanListDTO<>(idList, hasBan);
    }

    public BanListDTO() {
    }

    public BanListDTO(List<ID> idList, Boolean hasBan) {
        this.idList = idList;
        this.hasBan = hasBan;
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void setIdList(List<ID> idList) {
        this.idList = idList;
    }

    public Boolean getHasBan() {
        return hasBan;
    }

    public void setHasBan(Boolean ban) {
        this.hasBan = ban;
    }

}

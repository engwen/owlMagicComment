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
    private Boolean isBan;

    public BanListDTO(List<ID> idList, Boolean isBan) {
        this.idList = idList;
        this.isBan = isBan;
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void setIdList(List<ID> idList) {
        this.idList = idList;
    }

    public Boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(Boolean ban) {
        this.isBan = ban;
    }

}

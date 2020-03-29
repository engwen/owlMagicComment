package com.owl.mvc.dto;

/**
 * 禁用传递对象接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class BanDTO<ID> {
    /*
     * 对象id
     * */
    private ID id;
    /*
     * 是否禁用
     * */
    private Boolean isBan;

    public static <ID> BanDTO getInstance(ID id, Boolean isBan) {
        return new BanDTO<>(id, isBan);
    }

    public BanDTO() {
    }

    private BanDTO(ID id, Boolean isBan) {
        this.id = id;
        this.isBan = isBan;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(Boolean ban) {
        this.isBan = ban;
    }
}

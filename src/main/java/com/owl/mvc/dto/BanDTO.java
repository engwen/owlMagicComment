package com.owl.mvc.dto;

/**
 * 禁用传递对象接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class BanDTO {
    /*
    * 对象id
    * */
    private Long id;
    /*
    * 是否禁用
    * */
    private Boolean isBan;

    public BanDTO() {

    }

    private BanDTO(Long id, Boolean isBan) {
        this.id = id;
        this.isBan = isBan;
    }

    public static BanDTO getInstance(Long id, Boolean isBan) {
        return new BanDTO(id, isBan);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(Boolean ban) {
        this.isBan = ban;
    }
}

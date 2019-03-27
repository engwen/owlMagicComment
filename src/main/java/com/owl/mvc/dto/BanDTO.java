package com.owl.mvc.dto;

public class BanDTO {
    private Long id;
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

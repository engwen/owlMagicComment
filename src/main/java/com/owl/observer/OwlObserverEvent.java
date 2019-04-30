package com.owl.observer;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public class OwlObserverEvent {
    private int code;

    public OwlObserverEvent(int code) {
        this.code = code;
    }

    public static OwlObserverEvent DEFAULT_EVENT = new OwlObserverEvent(1);

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

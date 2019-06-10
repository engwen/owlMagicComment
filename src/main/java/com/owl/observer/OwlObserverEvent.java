package com.owl.observer;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public class OwlObserverEvent {
    private String eventName;

    public OwlObserverEvent(String eventName) {
        this.eventName = eventName;
    }

    public static OwlObserverEvent DEFAULT_EVENT = new OwlObserverEvent("默认事件");


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

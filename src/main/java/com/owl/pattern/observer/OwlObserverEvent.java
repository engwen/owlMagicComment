package com.owl.pattern.observer;

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

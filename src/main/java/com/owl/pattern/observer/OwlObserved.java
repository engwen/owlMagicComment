package com.owl.pattern.observer;

import com.owl.pattern.function.OwlListenCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 被观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserved {
    /**
     * 保證綫程安全
     */
    private Map<String, OwlListenCode> consumerMap = new ConcurrentHashMap<>();

    Map<String, OwlListenCode> getConsumerMap() {
        return consumerMap;
    }

    void setConsumerMap(Map<String, OwlListenCode> consumerMap) {
        this.consumerMap = consumerMap;
    }

    /**
     * 被觀察者監聽事件
     */
    public void addEventListen(OwlObserverEvent event, OwlListenCode listenCode) {
        //注冊驅動
        OwlObserverAB.addEventListen(event, this, listenCode);
    }

    public void removeEventListen(OwlObserverEvent event) {
        OwlObserverAB.removeEventListen(event, this);
    }

    public void removeAllEventListen() {
        OwlObserverAB.removeAllEventListenByObserved(this);
    }

    /**
     * 抛出
     */
    public void dispatchEvent(OwlObserverEvent owlObserverEvent) {
        OwlObserverAB.dispatchEvent(owlObserverEvent);
    }

    /**
     * 抛出
     */
    public void dispatchEvent(OwlObserverEvent owlObserverEvent, Class classModel) {
        OwlObserverAB.dispatchEvent(owlObserverEvent, classModel);
    }

    /*------------------           以下方法提供給 OwlObserverAB 用於數據同步/脚本執行           ---------------------*/

    /**
     * 移除指定事件
     */
    void removeListenByEvent(OwlObserverEvent event) {
        if (null != consumerMap.get(event.getEventName())) {
            consumerMap.remove(event.getEventName());
        }
    }

    /**
     * 移除所有事件
     */
    void removeAllListen() {
        consumerMap = null;
        consumerMap = new ConcurrentHashMap<>();
    }

    /**
     * 被觀察者需要执行的代碼
     */
    void startDoing(OwlObserverEvent event) {
        OwlListenCode consumer = consumerMap.get(event.getEventName());
        if (null != consumer) {
            //執行
            consumer.startDoing();
        }
    }
}

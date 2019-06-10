package com.owl.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 被观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserved {

    private Map<String, Consumer<OwlObserved>> consumerMap = new HashMap<>();

    public Map<String, Consumer<OwlObserved>> getConsumerMap() {
        return consumerMap;
    }

    //被觀察者監聽事件
    public void addEventListen(OwlObserverEvent event, Consumer<OwlObserved> consumer) {
        //添加事件处理方法记录
        consumerMap.put(event.getEventName(), consumer);
        //注冊驅動
        OwlObserverAB.addEventListen(event, this);
    }

    public void removeListen(OwlObserverEvent event) {
        removeListenByEvent(event);
        OwlObserverAB.removeEventListen(event, this);
    }

    //抛出
    public void dispatchEvent(OwlObserverEvent owlObserverEvent) {
        OwlObserverAB.dispatchEvent(owlObserverEvent);
    }

/*------------------           以下方法提供給 OwlObserverAB 用於數據同步/脚本執行           ---------------------*/
    //移除指定事件
    void removeListenByEvent(OwlObserverEvent event) {
        if (null != consumerMap.get(event.getEventName())) {
            consumerMap.remove(event.getEventName());
        }
    }

    //移除所有事件
    void removeAllListen() {
        consumerMap = null;
        consumerMap = new HashMap<>();
    }

    //被觀察者需要执行的代碼
    void startDoing(OwlObserverEvent event) {
        Consumer<OwlObserved> consumer = consumerMap.get(event.getEventName());
        if (null != consumer) {
            //執行
            consumer.accept(this);
        }
    }
}

package com.owl.pattern.observer;

import com.owl.factory.OwlThreadPool;
import com.owl.pattern.function.OwlListenCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * 觀察者
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/11.
 */
public abstract class OwlObserverUtil {
    static Map<String, Map<Object, OwlListenCode>> observer = new ConcurrentHashMap<>();

    //被觀察者監聽事件
    public static void addEventListen(OwlObserverEvent event, Object obj, OwlListenCode listenCode) {
        Map<Object, OwlListenCode> objectConsumerMap = observer.computeIfAbsent(event.getEventName(), k -> new ConcurrentHashMap<>());
        objectConsumerMap.put(obj, listenCode);
    }

    public static void removeEventListen(OwlObserverEvent event) {
        OwlObserverAB.removeEventListen(event);
    }

    //移除所有事件
    public static void removeAllListen() {
        OwlObserverAB.removeAllEventListen();
    }

    public static void dispatchEvent(OwlObserverEvent event) {
        OwlObserverAB.dispatchEvent(event);
    }

    public static void dispatchEvent(OwlObserverEvent event, Class classModel) {
        OwlObserverAB.dispatchEvent(event, classModel);
    }

    static void dispatchEvent(OwlObserverEvent event, Predicate<Object> predicate) {
        Map<Object, OwlListenCode> tempMap = observer.get(event.getEventName());
        if (null != tempMap) {
            tempMap.keySet().forEach(key -> {
                        if (predicate.test(key)) {
                            OwlThreadPool.getThreadPool().execute(() -> tempMap.get(key).startDoing());
                        }
                    }
            );
        }
    }
}

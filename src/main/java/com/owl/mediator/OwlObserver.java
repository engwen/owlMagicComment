package com.owl.mediator;

import com.owl.observer.OwlObserverEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 觀察者
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/11.
 */
public abstract class OwlObserver {
    private static Map<String, Map<Object, Consumer<Object>>> observer = new ConcurrentHashMap<>();

    //被觀察者監聽事件
    public static void addEventListen(OwlObserverEvent event, Object obj, Consumer<Object> consumer) {
        Map<Object, Consumer<Object>> objectConsumerMap = observer.computeIfAbsent(event.getEventName(), k -> new ConcurrentHashMap<>());
        objectConsumerMap.put(obj, consumer);
    }

    public static void removeEventListen(OwlObserverEvent event, Object obj) {
        if (null != observer.get(event.getEventName())) {
            observer.get(event.getEventName()).remove(obj);
        }
    }

    public static void removeEventListen(OwlObserverEvent event) {
        if (null != observer.get(event.getEventName())) {
            observer.remove(event.getEventName());
        }
    }

    //移除所有事件
    public static void removeAllListen() {
        observer = null;
        observer = new ConcurrentHashMap<>();
    }

    public static void dispatchEvent(OwlObserverEvent event) {
        dispatchEvent(event, (obj) -> true);
    }

    public static void dispatchEvent(OwlObserverEvent event, Class classModel) {
        dispatchEvent(event, (obj) -> obj.getClass().equals(classModel));
    }

    private static void dispatchEvent(OwlObserverEvent event, Predicate<Object> predicate) {
        Map<Object, Consumer<Object>> tempMap = observer.get(event.getEventName());
        if (null != tempMap) {
            tempMap.keySet().forEach(key -> {
                        if (predicate.test(key)) {
                            tempMap.get(key).accept(tempMap.get(key));
                        }
                    }
            );
        }
    }
}

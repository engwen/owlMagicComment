package com.owl.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * 观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserverAB {
    private static Logger logger = Logger.getLogger(OwlObserverAB.class.getName());
    private static Map<String, Set<OwlObserved>> mapList = new HashMap<>();

    //添加对象监听
    static void addEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model) {
        //監聽對象注冊
        if (!mapList.keySet().contains(owlObserverEvent.getEventName())) {
            mapList.put(owlObserverEvent.getEventName(), new HashSet<>());
        }
        mapList.get(owlObserverEvent.getEventName()).add(model);
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model) {
        removeEventList(owlObserverEvent, obj -> model == obj);
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent owlObserverEvent, Class classModel) {
        removeEventList(owlObserverEvent, obj -> classModel.equals(obj.getClass()));
    }

    private static void removeEventList(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate) {
        logger.info("移除事件监听：" + owlObserverEvent.getEventName());
        if (!mapList.keySet().contains(owlObserverEvent.getEventName())) {
            return;
        }
        AtomicReference<OwlObserved> temp = new AtomicReference<>();
        mapList.get(owlObserverEvent.getEventName()).forEach(it -> {
            if (predicate.test(it)) {
                it.removeListenByEvent(owlObserverEvent);
                temp.set(it);
            }
        });
        mapList.get(owlObserverEvent.getEventName()).remove(temp.get());
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent owlObserverEvent) {
        logger.info("移除指定事件的全部监听：" + owlObserverEvent.getEventName());
        if (!mapList.keySet().contains(owlObserverEvent.getEventName())) {
            return;
        }
        mapList.get(owlObserverEvent.getEventName()).forEach(it -> it.removeListenByEvent(owlObserverEvent));
        mapList.remove(owlObserverEvent.getEventName());
    }

    //移除監聽
    public static void removeAllEventListen(OwlObserved model) {
        logger.info(String.format("移除%s的全部事件监听", model.getClass().getName()));
        mapList.keySet().forEach(key ->
                mapList.get(key).forEach(owlObserved -> {
                    if (owlObserved == model) {
                        mapList.get(key).remove(owlObserved);
                    }
                })
        );
        model.removeAllListen();
    }

    //抛出
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent) {
        dispatchEvent(owlObserverEvent, obj -> true);
    }

    //抛出
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent, Class classModel) {
        dispatchEvent(owlObserverEvent, obj -> obj.getClass().equals(classModel));
    }

    private static void dispatchEvent(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate) {
        logger.info("抛出事件监听：" + owlObserverEvent.getEventName());
        if (null != mapList.get(owlObserverEvent.getEventName())) {
            mapList.get(owlObserverEvent.getEventName()).forEach(it -> {
                if (predicate.test(it)) {
                    it.startDoing(owlObserverEvent);
                }
            });
        }
    }
}

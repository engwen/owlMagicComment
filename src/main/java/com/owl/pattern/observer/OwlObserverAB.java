package com.owl.pattern.observer;

import com.owl.factory.OwlThreadPool;
import com.owl.pattern.observer.simplify.OwlObserverUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * 观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserverAB {
    /**
     * 保證綫程安全
     */
    private static Map<String, Set<OwlObserved>> mapList = new ConcurrentHashMap<>();

    /**
     * 添加对象监听
     */
    static void addEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model) {
        //監聽對象注冊
        mapList.putIfAbsent(owlObserverEvent.getEventName(), new HashSet<>());
        mapList.get(owlObserverEvent.getEventName()).add(model);
    }

    /**
     * 移除監聽
     */
    static void removeEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model) {
        removeEventList(owlObserverEvent, obj -> model == obj);
    }

    /**
     * 移除監聽
     */
    public static void removeEventListen(OwlObserverEvent owlObserverEvent, Class classModel) {
        removeEventList(owlObserverEvent, obj -> classModel.equals(obj.getClass()));
    }

    private static void removeEventList(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate) {
        if (!mapList.keySet().contains(owlObserverEvent.getEventName())) {
            return;
        }
        mapList.get(owlObserverEvent.getEventName()).forEach(it -> {
            if (predicate.test(it)) {
                it.removeListenByEvent(owlObserverEvent);
                mapList.get(owlObserverEvent.getEventName()).remove(it);
            }
        });
    }

    /**
     * 移除監聽
     */
    public static void removeEventListen(OwlObserverEvent owlObserverEvent) {
        if (!mapList.keySet().contains(owlObserverEvent.getEventName())) {
            return;
        }
        mapList.get(owlObserverEvent.getEventName()).forEach(it -> it.removeListenByEvent(owlObserverEvent));
        mapList.remove(owlObserverEvent.getEventName());
    }

    /**
     * 移除監聽
     */
    public static void removeAllEventListen() {
        mapList.values().forEach((Set<OwlObserved> it) -> it.forEach(OwlObserved::removeAllListen));
        mapList = null;
        mapList = new ConcurrentHashMap<>();
    }

    /**
     * 移除監聽
     */
    public static void removeAllEventListenByObserved(OwlObserved... models) {
        List<OwlObserved> modelList = Arrays.asList(models);
        mapList.keySet().forEach(key ->
                mapList.get(key).forEach(owlObserved -> {
                    if (modelList.contains(owlObserved)) {
                        mapList.get(key).remove(owlObserved);
                    }
                })
        );
        modelList.forEach(OwlObserved::removeAllListen);
    }

    /**
     * 抛出
     */
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent) {
        dispatchEvent(owlObserverEvent, obj -> true);
        OwlObserverUtil.dispatchEvent(owlObserverEvent, (obj) -> true);
    }

    /**
     * 抛出
     */
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent, Class classModel) {
        dispatchEvent(owlObserverEvent, obj -> obj.getClass().equals(classModel));
        OwlObserverUtil.dispatchEvent(owlObserverEvent, obj -> obj.getClass().equals(classModel));
    }

    private static void dispatchEvent(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate) {
        Set<OwlObserved> observedSet = null == mapList.get(owlObserverEvent.getEventName()) ? null : new HashSet<>(mapList.get(owlObserverEvent.getEventName()));

        if (null != observedSet) {
            observedSet.forEach(it -> {
                if (predicate.test(it)) {
                    OwlThreadPool.getThreadPool().execute(() -> it.startDoing(owlObserverEvent));
                }
            });
//            new Thread(() ->
//                    observedSet.forEach(it -> {
//                        if (predicate.test(it)) {
//                            it.startDoing(owlObserverEvent);
//                        }
//                    })
//            ).start();
        }
    }
}

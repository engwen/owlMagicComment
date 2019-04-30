package com.owl.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserverAB {
    private static Map<Integer, Set<OwlObserved>> mapList = new HashMap<>();

    //添加监听
    public static void addEventListen(OwlObserverEvent code, OwlObserved model) {
        if (!mapList.keySet().contains(code.getCode())) {
            mapList.put(code.getCode(), new HashSet<>());
        }
        mapList.get(code.getCode()).add(model);
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent code, OwlObserved model) {
        if (!mapList.keySet().contains(code.getCode())) {
            return;
        }
        AtomicReference<OwlObserved> temp = new AtomicReference<>();
        mapList.get(code.getCode()).forEach(it -> {
            if (it.OBSERVED_CODE == model.OBSERVED_CODE) {
                temp.set(it);
            }
        });
        mapList.get(code.getCode()).remove(temp.get());
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent msg, Class classModel) {
        if (!mapList.keySet().contains(msg.getCode())) {
            return;
        }
        AtomicReference<OwlObserved> temp = new AtomicReference<>();
        mapList.get(msg.getCode()).forEach(it -> {
            if (it.getClass().equals(classModel)) {
                temp.set(it);
            }
        });
        mapList.get(msg.getCode()).remove(temp.get());
    }

    //移除監聽
    public static void removeEventListen(OwlObserverEvent msg) {
        if (!mapList.keySet().contains(msg.getCode())) {
            return;
        }
        mapList.remove(msg.getCode());
    }

    //抛出監聽
    public static void dispatchEvent(OwlObserverEvent msg) {
        if (null != mapList.get(msg.getCode())) {
            mapList.get(msg.getCode()).forEach(OwlObserved::listenIng);
        }
    }

    //抛出監聽
    public static void dispatchEvent(OwlObserverEvent msg, Class classModel) {
        if (null != mapList.get(msg.getCode())) {
            mapList.get(msg.getCode()).forEach(it -> {
                if (it.getClass().equals(classModel)) {
                    it.listenIng();
                }
            });
        }
    }
}

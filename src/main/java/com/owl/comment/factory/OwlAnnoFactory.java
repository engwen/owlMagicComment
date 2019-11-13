package com.owl.comment.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器工厂
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/19.
 */
public class OwlAnnoFactory {
    /**
     * 容器对象
     */
    private static final Map<String,Object> owlContainer = new ConcurrentHashMap<>();

    static void init(String packageName){

    }

}

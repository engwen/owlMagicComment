package com.owl.mvc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/3/21.
 */
@Configuration
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeans(T t) {
        return (T) applicationContext.getBean(t.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeans(String name) {
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class c) {
        return (T) applicationContext.getBean(c);
    }
}

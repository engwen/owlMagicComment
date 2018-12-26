package com.owl.annotations;

import java.lang.annotation.*;

/**
 * 添加日志注解
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/16.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlLogInfo {
    String value() default "";
}

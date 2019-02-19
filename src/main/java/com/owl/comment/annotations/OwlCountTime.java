package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * 计算时间
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/30.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlCountTime {

    String classPath() default "";

    String methodName() default "";
}

package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * 将指定的参数设置为 null
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/20.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlSetNullData {
    String[] paramsValue() default {};

    String[] backValue() default {};
}

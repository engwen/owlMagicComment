package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * try catch解决
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/16.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlTry {
    String value() default "";
}

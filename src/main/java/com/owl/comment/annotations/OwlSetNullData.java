package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
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

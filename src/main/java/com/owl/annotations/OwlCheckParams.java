package com.owl.annotations;

import java.lang.annotation.*;

/**
 * 添加參數注解
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/15.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlCheckParams {

    String[] notAllNull() default {};

    String[] notNull() default {};

    String[] canNull() default {};
}

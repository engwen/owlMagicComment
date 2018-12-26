package com.owl.annotations;

import java.lang.annotation.*;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/7.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlBackToMsgResult {
    String msg() default "";

    String code() default "";

    String result() default "";

    String data() default "";
}

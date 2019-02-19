package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/10.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlBackToObject {
    String classPath() default "";

    String msg() default "";

    String code() default "";

    String result() default "";

    String data() default "";
}

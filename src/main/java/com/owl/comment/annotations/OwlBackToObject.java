package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * 改为指定的返回值类型
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/10.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlBackToObject {
    String classPath() default "";

    String msg() default "";

    String code() default "";

    String result() default "";

    String data() default "";

    String oldMsg() default "";

    String oldCode() default "";

    String oldResult() default "";

    String oldData() default "";

}

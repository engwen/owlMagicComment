package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * 改为 MsgResultVO 返回数据
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/7.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlBackToMsgResult {
    String msg() default "";

    String code() default "";

    String result() default "";

    String data() default "";
}

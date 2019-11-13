package com.owl.comment.annotations.backmsg;

import java.lang.annotation.*;

/**
 * 设置唯一返回值
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/19.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlBackMsg {

}


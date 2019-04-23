package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/17.
 */
@Target({ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlSetService {
    String value() default "";
}

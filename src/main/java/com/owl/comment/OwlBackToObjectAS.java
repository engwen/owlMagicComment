package com.owl.comment;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/10.
 */

@Aspect
@Component
@Order(97)
public class OwlBackToObjectAS {
    private static Logger logger = Logger.getLogger(OwlBackToObjectAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.OwlBackToObject)")
    public void changeBackClassCut() {
    }
}

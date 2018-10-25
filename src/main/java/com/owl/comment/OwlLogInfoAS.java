package com.owl.comment;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 使用切片管理controller層的日志
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/16.
 */
@Aspect
@Component
@Order(90)
public class OwlLogInfoAS {
    private static Logger logger = Logger.getLogger(OwlLogInfoAS.class.getName());

    //    @Pointcut("@annotation(com.owl.shiro.aop.OwlLogInfo)")
    @Pointcut("execution(* com.owl.comment.*.*(..))")
    public void logInfo() {
    }

    @Before("logInfo()")
    public void checkParams(JoinPoint joinPoint) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        logger.info("----------------------" + request.getMethod());
//        Signature signature = joinPoint.getSignature();
        logger.info(String.format("now in class %s , method %s", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()));
    }
}

package com.owl.comment;

import com.owl.magicUtil.util.RegexUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Pointcut("@within(com.owl.comment.OwlLogInfo)")
    public void logCut() {
    }

    @Before("logCut()")
    public void logInfo(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger temp = logger;
        try {
            temp = Logger.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        } catch (Exception e) {
            temp.info("get class logger error,we will use aop OwlLogInfo default logger");
        }
        OwlLogInfo owlLogInfo = methodSignature.getMethod().getAnnotation(OwlLogInfo.class);
        //方法注解
        if (null != owlLogInfo && !RegexUtil.isEmpty(owlLogInfo.value())) {
            temp.info(owlLogInfo.value());
        } else {
//          joinPoint.getSignature().getDeclaringTypeName(),
            temp.info(String.format("now in %s", joinPoint.getSignature().getName()));
        }
    }
}

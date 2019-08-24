package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlLogInfo;
import com.owl.comment.utils.AsLogUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
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

    @Pointcut("@within(com.owl.comment.annotations.OwlLogInfo)")
    public void logCut() {
    }

    @Before("logCut()")
    public void logInfo(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        OwlLogInfo owlLogInfo = methodSignature.getMethod().getAnnotation(OwlLogInfo.class);

        if (null == owlLogInfo) {
            owlLogInfo = AnnotationUtils.findAnnotation(methodSignature.getMethod().getDeclaringClass(), OwlLogInfo.class);
        }
        //方法注解
        if (null != owlLogInfo && !RegexUtil.isEmpty(owlLogInfo.value())) {
            AsLogUtil.info(joinPoint, owlLogInfo.value());
        } else {
//          joinPoint.getSignature().getDeclaringTypeName(),
            AsLogUtil.info(joinPoint, String.format("now in %s", joinPoint.getSignature().getName()));
        }
    }
}

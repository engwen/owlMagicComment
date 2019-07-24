package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlCountTime;
import com.owl.comment.utils.AsLogUtil;
import com.owl.magicUtil.util.RegexUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/30.
 */
@Aspect
@Component
@Order(90)
public class OwlCountTimeAS {
    private static final double ONE_MINUTE = 1000;
    private Date startTime;

    @Pointcut("@annotation(com.owl.comment.annotations.OwlCountTime)")
    public void countTimeCut() {
    }

    @Before("countTimeCut()")
    public void logStartTime(JoinPoint joinPoint) {
        startTime = new Date();
    }

    @After("countTimeCut()")
    public void logEndTime(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        OwlCountTime countTime = methodSignature.getMethod().getAnnotation(OwlCountTime.class);
        Double second = ((new Date()).getTime() - startTime.getTime()) / ONE_MINUTE;
        if (!RegexUtil.isParamsHaveEmpty(countTime.classPath(), countTime.methodName())) {

        } else {
            AsLogUtil.info(joinPoint, String.format("method name: %s cost: %s seconds", joinPoint.getSignature().getName(), second));
        }
    }
}

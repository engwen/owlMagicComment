package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlSetService;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.mvc.utils.SpringContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/17.
 */
@Aspect
@Component
@Order(70)
public class OwlSetServiceAS {
    @Pointcut("@annotation(com.owl.comment.annotations.OwlSetService)")
    public void setServiceCut() {
    }

    @Before("setServiceCut()")
    public void setService(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String serviceName = methodSignature.getMethod().getAnnotation(OwlSetService.class).value();
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(methodSignature.getMethod().getAnnotation(OwlSetService.class));
        Object[] args = joinPoint.getArgs();
        if (RegexUtil.isEmpty(serviceName)) {
            invocationHandler = SpringContextUtil.getBean(invocationHandler.getClass());
        } else {
            invocationHandler = SpringContextUtil.getBeans(serviceName);
        }
    }
}

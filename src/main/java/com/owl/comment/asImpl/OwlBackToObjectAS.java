package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlBackToObject;
import com.owl.magicUtil.util.ObjectUtil;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.mvc.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    @Pointcut("@annotation(com.owl.comment.annotations.OwlBackToObject)")
    public void changeBackClassCut() {
    }

    @Around("changeBackClassCut()")
    public Object changeBackClass(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object obj = joinPoint.proceed();
        Object result = obj;
        String classPath = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).classPath();
        String codeName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).code();
        String msgName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).msg();
        String dataName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).data();
        String resultName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).result();
        if (RegexUtil.isEmpty(classPath)) {
            logger.error("未指定转换对象");
        } else {
            try {
                if (obj instanceof MsgResultVO) {
                    MsgResultVO temp = (MsgResultVO) obj;
                    result = Class.forName(classPath).newInstance();
                    if (!RegexUtil.isEmpty(codeName)) {
                        ObjectUtil.setProValue(codeName, temp.getResultCode(), result);
                    }
                    if (!RegexUtil.isEmpty(msgName)) {
                        ObjectUtil.setProValue(msgName, temp.getResultMsg(), result);
                    }
                    if (!RegexUtil.isEmpty(dataName)) {
                        ObjectUtil.setProValue(dataName, temp.getResultData(), result);
                    }
                    if (!RegexUtil.isEmpty(resultName)) {
                        ObjectUtil.setProValue(resultName, temp.getResult(), result);
                    }
                } else {
                    logger.error("本注仅适用于将MsgResultVO类型转化为指定类型");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("转化出错");
            }
        }
        return result;
    }
}

package com.owl.comment;

import com.owl.magicUtil.util.ClassTypeUtil;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.magicUtil.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Description;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/20.
 */
@Aspect
@Component
@Order(92)
public class OwlSetNullDataAS {
    private static Logger logger = Logger.getLogger(OwlSetNullDataAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.OwlSetNullData)")
    public void setNullDataCut() {
    }

    @Around("setNullDataCut()")
    public Object setNullData(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("设置指定的返回参数为空");
        Object obj = joinPoint.proceed();
        if (obj instanceof MsgResultVO) {
            MsgResultVO resultVO = (MsgResultVO) obj;
            Object resultDataObj = resultVO.getResultData();
            if (!RegexUtil.isEmpty(resultDataObj)) {
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                String[] setNullDatas = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).values();
                if (ClassTypeUtil.isBaseClass(resultDataObj) || ClassTypeUtil.isPackClass(resultDataObj) || resultDataObj instanceof Collection) {
                    logger.error("不支持除 resultData 为基础类型及其包装类或是集合的对象");
                } else if (resultDataObj instanceof Map) {
                    logger.info("支持 resultData 为 Map<String,Pack> 的对象，开始置空");
                    Map temp = (Map) resultDataObj;
                    for (String param : setNullDatas) {
                        temp.put(param, null);
                    }
                } else {
                    logger.info("支持 resultData 为 Class 的对象，开始反射置空");
                    Field[] fields = resultDataObj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        for (String param : setNullDatas) {
                            if (param.equals(field.getName())) {
                                String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                                Method method = resultDataObj.getClass().getDeclaredMethod(methodName, field.getType());
                                if (field.getType().equals(String.class)) {
                                    String temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(Long.class)) {
                                    Long temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(Integer.class)) {
                                    Integer temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(Float.class)) {
                                    Float temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(Double.class)) {
                                    Double temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(List.class)) {
                                    List temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else if (field.getType().equals(Date.class)) {
                                    Date temp = null;
                                    method.invoke(resultDataObj, temp);
                                } else {
                                    logger.error(field.getType() + "类型不予支持");
                                }
                            }
                        }
                    }
                }
            }
        } else {
            logger.error("不支持除MsgResultVO以外的对象");
        }
        return obj;
    }
}

package com.owl.asImpl;

import com.owl.annotations.OwlSetNullData;
import com.owl.magicUtil.util.ClassTypeUtil;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.magicUtil.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/20.
 */
@Aspect
@Component
@Order(95)
public class OwlSetNullDataAS {
    private static Logger logger = Logger.getLogger(OwlSetNullDataAS.class.getName());

    @Pointcut("@annotation(com.owl.annotations.OwlSetNullData)")
    public void setNullDataCut() {
    }

    @Around("setNullDataCut()")
    public Object setNullData(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] setNullParams = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).paramsValue();
        String[] setNullDatas = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).backValue();
        if (setNullParams.length > 0) {
            logger.debug("设定指定的请求参数为空");
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, String[]> paramsHeadMap = request.getParameterMap();
            if (null != paramsHeadMap && paramsHeadMap.keySet().size() > 0) {
                for (String param : setNullParams) {
                    paramsHeadMap.put(param, null);
                }
            } else {
//          requestBody中獲取參數
                Object paramsVO = joinPoint.getArgs()[0];
                if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                    logger.debug("本注解仅限使用对象或Map接收参数时使用");
                } else {
//                使用Map接收参数
                    if (paramsVO instanceof Map) {
                        Map<String, Object> paramsBodyMap = (Map<String, Object>) paramsVO;
                        for (String param : setNullParams) {
                            paramsBodyMap.put(param, null);
                        }
                    } else {
//                  使用对象接收参数
                        Field[] fields = paramsVO.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            for (String param : setNullParams) {
                                if (field.getName().equals(param)) {
                                    field.setAccessible(true);
                                    String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                                    Method method = paramsVO.getClass().getDeclaredMethod(methodName, field.getType());
                                    setNull(method, paramsVO, field.getType());
                                }
                            }
                        }
                    }
                }
            }
        }
        Object obj = joinPoint.proceed();
        if (setNullDatas.length > 0) {
            logger.debug("设置指定的返回参数为空");
            if (obj instanceof MsgResultVO) {
                MsgResultVO resultVO = (MsgResultVO) obj;
                Object resultDataObj = resultVO.getResultData();
                if (!RegexUtil.isEmpty(resultDataObj)) {
                    if (ClassTypeUtil.isBaseClass(resultDataObj) || ClassTypeUtil.isPackClass(resultDataObj) || resultDataObj instanceof Collection) {
                        logger.error("不支持除 resultData 为基础类型及其包装类或是集合的对象");
                    } else if (resultDataObj instanceof Map) {
                        logger.info("支持 resultData 为 Map<String,Pack> 的对象，开始置空");
                        Map<String, String> temp = (Map<String, String>) resultDataObj;
                        for (String param : setNullDatas) {
                            if (ClassTypeUtil.isBaseClass(temp.get(param))) {
                                logger.error("不支持除 Map的value 为基础类型及其包装类或是集合的对象");
                            } else {
                                temp.put(param, null);
                            }
                        }
                    } else {
                        logger.info("支持 resultData 为 Class 的对象，开始反射置空");
                        Field[] fields = resultDataObj.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            for (String param : setNullDatas) {
                                if (param.equals(field.getName())) {
                                    field.setAccessible(true);
                                    String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                                    Method method = resultDataObj.getClass().getDeclaredMethod(methodName, field.getType());
                                    setNull(method, resultDataObj, field.getType());
                                }
                            }
                        }
                    }
                }
            } else {
                logger.error("不支持除MsgResultVO以外的对象");
            }
        }
        return obj;
    }

    private static void setNull(Method method, Object obj, Class className) throws Exception {
        if (className.equals(String.class)) {
            method.invoke(obj, (String) null);
        } else if (className.equals(Long.class)) {
            method.invoke(obj, (Long) null);
        } else if (className.equals(Integer.class)) {
            method.invoke(obj, (Integer) null);
        } else if (className.equals(Float.class)) {
            method.invoke(obj, (Float) null);
        } else if (className.equals(Double.class)) {
            method.invoke(obj, (Double) null);
        } else if (className.equals(List.class)) {
            method.invoke(obj, (List) null);
        } else if (className.equals(Date.class)) {
            method.invoke(obj, (Date) null);
        } else {
            logger.error(className + "类型不予支持");
        }
    }
}

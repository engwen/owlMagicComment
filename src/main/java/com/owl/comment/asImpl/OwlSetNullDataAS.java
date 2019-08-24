package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlSetNullData;
import com.owl.comment.utils.AsLogUtil;
import com.owl.util.ClassTypeUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

    @Pointcut("@annotation(com.owl.comment.annotations.OwlSetNullData)")
    public void setNullDataCut() {
    }

    @Around("setNullDataCut()")
    public Object setNullData(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] setNullParams = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).paramsValue();
        String[] setNullDatas = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).backValue();
        if (setNullParams.length > 0) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, String[]> paramsHeadMap = request.getParameterMap();
            if (null != paramsHeadMap && paramsHeadMap.keySet().size() > 0) {
                for (String param : setNullParams) {
                    paramsHeadMap.put(param, null);
                }
            } else {
//          从对象中獲取參數
                Object paramsVO = joinPoint.getArgs()[0];
                if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                    AsLogUtil.error(joinPoint, "This annotation is limited to objects or Maps that receive parameters");
                } else {
//                使用Map接收参数
                    if (paramsVO instanceof Map) {
                        Map<String, Object> paramsBodyMap = (Map<String, Object>) paramsVO;
                        for (String param : setNullParams) {
                            paramsBodyMap.put(param, null);
                        }
                    } else {
//                  使用对象接收参数
                        Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
                        for (Field field : fields) {
                            for (String param : setNullParams) {
                                if (field.getName().equals(param)) {
                                    field.setAccessible(true);
                                    String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                                    Method method = paramsVO.getClass().getDeclaredMethod(methodName, field.getType());
                                    setNullByField(method, paramsVO, field.getType());
                                }
                            }
                        }
                    }
                }
            }
        }
        Object obj = joinPoint.proceed();
        if (setNullDatas.length > 0) {
            setNullByObject(setNullDatas, obj);
        }
        return obj;
    }

    private static void setNullByObject(String[] setNullDatas, Object resultDataObj) throws Exception {
        Field[] fields = ObjectUtil.getSupperClassProperties(resultDataObj, new Field[0]);
        if (!RegexUtil.isEmpty(resultDataObj)) {
            if (ClassTypeUtil.isBaseClass(resultDataObj) || ClassTypeUtil.isPackClass(resultDataObj)) {
                logger.warning("Objects of base type and other wrapper classes other than resultData are not supported");
            } else if (resultDataObj instanceof MsgResultVO) {
                MsgResultVO resultVO = (MsgResultVO) resultDataObj;
                Object obj = resultVO.getResultData();
                if (!RegexUtil.isEmpty(obj)) {
                    setNullByObject(setNullDatas, obj);
                }
            } else if (resultDataObj instanceof PageVO) {
                PageVO pageVO = (PageVO) resultDataObj;
                Object obj = pageVO.getObjectList();
                if (!RegexUtil.isEmpty(obj)) {
                    setNullByObject(setNullDatas, obj);
                }
            } else if (resultDataObj instanceof Collection) {
                setNullByList(setNullDatas, resultDataObj, fields);
            } else if (resultDataObj instanceof Map) {
                setNullByMap(setNullDatas, resultDataObj);
            } else {
                for (Field field : fields) {
                    for (String param : setNullDatas) {
                        if (param.equals(field.getName())) {
                            field.setAccessible(true);
                            String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                            Method method = resultDataObj.getClass().getDeclaredMethod(methodName, field.getType());
                            setNullByField(method, resultDataObj, field.getType());
                        }
                    }
                }
            }
        }
    }

    private static void setNullByList(String[] setNullDatas, Object resultDataObj, Field[] fields) throws Exception {
        List temp = (List) resultDataObj;
        for (Field field : fields) {
            for (String param : setNullDatas) {
                if (param.equals(field.getName())) {
                    field.setAccessible(true);
                    for (Object objTemp : temp) {
                        String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                        Method method = resultDataObj.getClass().getDeclaredMethod(methodName, field.getType());
                        setNullByField(method, objTemp, field.getType());
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void setNullByMap(String[] setNullDatas, Object resultDataObj) {
        Map<String, Object> temp = (Map<String, Object>) resultDataObj;
        for (String param : setNullDatas) {
            if (ClassTypeUtil.isBaseClass(temp.get(param))) {
                logger.warning("Objects that do not support base types other than Map value and their wrapper classes or collections");
            } else {
                temp.put(param, null);
            }
        }
    }

    private static void setNullByField(Method method, Object obj, Class className) throws Exception {
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
            logger.warning(className + "Type not supported");
        }
    }
}

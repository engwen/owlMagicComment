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
        String classPath = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).classPath();
        String codeName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).code();
        String msgName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).msg();
        String dataName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).data();
        String resultName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).result();

        Object obj = joinPoint.proceed();
        Object result = null;
        try {
            if (obj instanceof MsgResultVO) {
                MsgResultVO temp = (MsgResultVO) obj;
                result = Class.forName(classPath).newInstance();
                setProValue(codeName, temp.getResultCode(), result);
                setProValue(msgName, temp.getResultMsg(), result);
                setProValue(dataName, temp.getResultData(), result);
                setProValue(resultName, temp.getResult(), result);
            } else {
                logger.error("本注仅适用于将MsgResultVO类型转化为指定类型");
                result = obj;
            }
        } catch (Exception e) {
            result = obj;
            e.printStackTrace();
            logger.error("转化出错");
        }
        return result;
    }

    private static void setProValue(String proName, Object value, Object obj) throws Exception {
        if (!RegexUtil.isEmpty(proName)) {
            String setMethodStr = "set" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
            Field[] fields = ObjectUtil.getSupperClassProperties(obj, new Field[0]);
            for (Field field : fields) {
                if (proName.equals(field.getName()) && !RegexUtil.isEmpty(value)) {
                    try {
                        Method setMethod = obj.getClass().getDeclaredMethod(setMethodStr, value.getClass());
                        setMethod.invoke(obj, value);
                    } catch (NoSuchMethodException e) {
                        logger.debug("没有查询到对应属性方法,尝试进行Object对象插入。此方法同样适用适用泛型对象");
                        try {
                            Method setMethod = obj.getClass().getDeclaredMethod(setMethodStr, Object.class);
                            setMethod.invoke(obj, value);
                        } catch (NoSuchMethodException ex) {
                            logger.error(String.format("插入%s属性失败，请检查方法%s的返回类型，并确保类型一致", proName, setMethodStr));
                            throw ex;
                        }
                    }
                    break;
                }
            }
        }
    }
}

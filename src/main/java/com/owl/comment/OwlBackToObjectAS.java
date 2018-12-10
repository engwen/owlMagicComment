package com.owl.comment;

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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author engwen
 *         email xiachanzou@outlook.com
 *         2018/12/10.
 */

@Aspect
@Component
@Order(97)
public class OwlBackToObjectAS {
    private static Logger logger = Logger.getLogger(OwlBackToObjectAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.OwlBackToObject)")
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
                setProValue(msgName, temp.getResultData(), result);
                setProValue(dataName, temp.getResultData(), result);
                setProValue(resultName, temp.getResultData(), result);
            } else {
                logger.error("本注仅适用于将MsgResultVO类型转化为指定类型");
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
            String getMethodStr = "set" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (proName.equals(field.getName())) {
                    Method getMethod = obj.getClass().getDeclaredMethod(getMethodStr, value.getClass());
                    getMethod.invoke(obj, value);
                    break;
                }
            }
        }
    }
}

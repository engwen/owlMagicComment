package com.owl.asImpl;

import com.owl.annotations.OwlBackToMsgResult;
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
 * email xiachanzou@outlook.com
 * 2018/12/7.
 */
@Aspect
@Component
@Order(94)
public class OwlBackToMsgResultAS {
    private static Logger logger = Logger.getLogger(OwlBackToMsgResultAS.class.getName());

    @Pointcut("@annotation(com.owl.annotations.OwlBackToMsgResult)")
    public void changeBackClassCut() {
    }

    @Around("changeBackClassCut()")
    public Object changeBackClass(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String codeName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).code();
        String msgName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).msg();
        String dataName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).data();
        String resultName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).result();

        Object obj = joinPoint.proceed();
        MsgResultVO<Object> result = new MsgResultVO<>();
        try {
            result.setResult((Boolean) getProValue(resultName, obj));
            result.setResultMsg((String) getProValue(msgName, obj));
            result.setResultCode((String) getProValue(codeName, obj));
            result.setResultData(getProValue(dataName, obj));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("转化出错");
        }
        return result;
    }

    private static Object getProValue(String proName, Object obj) throws Exception {
        if (!RegexUtil.isEmpty(proName)) {
            String getMethodStr = "get" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (proName.equals(field.getName())) {
                    Method getMethod = obj.getClass().getDeclaredMethod(getMethodStr);
                    return getMethod.invoke(obj);
                }
            }
        }
        return null;
    }
}

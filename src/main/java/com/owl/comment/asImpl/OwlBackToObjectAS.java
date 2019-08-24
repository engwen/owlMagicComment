package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlBackToObject;
import com.owl.comment.utils.AsLogUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;
import com.owl.mvc.vo.MsgResultVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/10.
 */

@Aspect
@Component
@Order(97)
public class OwlBackToObjectAS {

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

        String oldCodeName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).oldCode();
        String oldMsgName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).oldMsg();
        String oldDataName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).oldData();
        String oldResultName = methodSignature.getMethod().getAnnotation(OwlBackToObject.class).oldResult();
        if (RegexUtil.isEmpty(classPath)) {
            AsLogUtil.error(joinPoint, "no conversion object is specified");
        } else {
            try {
                result = Class.forName(classPath).newInstance();
                if (obj instanceof MsgResultVO) {
                    MsgResultVO temp = (MsgResultVO) obj;
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
                } else if (!RegexUtil.isParamsAllEmpty(oldCodeName, oldMsgName, oldDataName, oldResultName)) {
                    if (!RegexUtil.isEmpty(codeName)) {
                        ObjectUtil.setProValue(codeName, ObjectUtil.getProValue(oldCodeName, obj), result);
                    }
                    if (!RegexUtil.isEmpty(msgName)) {
                        ObjectUtil.setProValue(msgName, ObjectUtil.getProValue(oldMsgName, obj), result);
                    }
                    if (!RegexUtil.isEmpty(dataName)) {
                        ObjectUtil.setProValue(dataName, ObjectUtil.getProValue(oldDataName, obj), result);
                    }
                    if (!RegexUtil.isEmpty(resultName)) {
                        ObjectUtil.setProValue(resultName, ObjectUtil.getProValue(oldResultName, obj), result);
                    }
                } else {
                    AsLogUtil.error(joinPoint, "This note applies only to the conversion of MsgResultVO types to specified types,or when you set old key names");
                }
            } catch (Exception e) {
                e.printStackTrace();
                AsLogUtil.error(joinPoint, "Conversion error");
            }
        }
        return result;
    }
}

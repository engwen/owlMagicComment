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
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/10.
 */

@Aspect
@Component
@Order(94)
public class OwlBackToObjectAS {

    @Pointcut("@within(com.owl.comment.annotations.OwlBackToObject)")
    public void changeBackClassCut() {
    }

    @Around("changeBackClassCut()")
    public Object changeBackClass(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object obj = joinPoint.proceed();
        Object result = obj;
        OwlBackToObject annotation = methodSignature.getMethod().getAnnotation(OwlBackToObject.class);
        if (null == annotation) {
            annotation = AnnotationUtils.findAnnotation(methodSignature.getMethod().getDeclaringClass(), OwlBackToObject.class);
        }
        if (null == annotation) {
            AsLogUtil.error(joinPoint, "@OwlBackToObject can`t all params are null");
            return obj;
        }
        String classPath = annotation.classPath();
        String codeName = annotation.code();
        String msgName = annotation.msg();
        String dataName = annotation.data();
        String resultName = annotation.result();

        String oldCodeName = annotation.oldCode();
        String oldMsgName = annotation.oldMsg();
        String oldDataName = annotation.oldData();
        String oldResultName = annotation.oldResult();
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

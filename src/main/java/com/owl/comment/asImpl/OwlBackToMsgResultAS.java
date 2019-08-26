package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlBackToMsgResult;
import com.owl.comment.utils.AsLogUtil;
import com.owl.util.ObjectUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.RegexUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
 * 2018/12/7.
 */
@Aspect
@Component
@Order(97)
public class OwlBackToMsgResultAS {


    @Pointcut("@within(com.owl.comment.annotations.OwlBackToMsgResult) || @annotation(com.owl.comment.annotations.OwlBackToMsgResult)")
    public void changeBackClassCut() {
    }

    @AfterThrowing(value = "changeBackClassCut()", throwing = "ex")
    public Object afterThrowing(JoinPoint joinPoint, Exception ex) {
        MsgResultVO<String> result = new MsgResultVO<>();
        result.errorResult(MsgConstant.CONTROLLER_THROWABLE_ERROR);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AsLogUtil.error(joinPoint, methodSignature.getMethod().getName() + "      " + ex);
        return result;
    }

    @Around("changeBackClassCut()")
    public Object changeBackClass(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();
        if (obj instanceof MsgResultVO)
            return obj;
        MsgResultVO<Object> result = new MsgResultVO<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //方法上存在注释
        OwlBackToMsgResult annotation = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class);
        if (null == annotation) {
            //尋找類上的注釋
            annotation = AnnotationUtils.findAnnotation(methodSignature.getMethod().getDeclaringClass(), OwlBackToMsgResult.class);
        }
        if (null == annotation) {
            AsLogUtil.error(joinPoint, "@OwlbackToMsgResult can`t all params are null");
            return obj;
        }
        String codeName = annotation.code();
        String msgName = annotation.msg();
        String dataName = annotation.data();
        String resultName = annotation.result();
        try {
            if (!RegexUtil.isEmpty(codeName)) {
                result.setResultCode((String) ObjectUtil.getProValue(codeName, obj));
            }
            if (!RegexUtil.isEmpty(msgName)) {
                result.setResultMsg((String) ObjectUtil.getProValue(msgName, obj));
            }
            if (!RegexUtil.isEmpty(dataName)) {
                result.setResultData(ObjectUtil.getProValue(dataName, obj));
            }
            if (!RegexUtil.isEmpty(resultName)) {
                result.setResult((Boolean) ObjectUtil.getProValue(resultName, obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AsLogUtil.error(joinPoint, "Conversion error");
            return obj;
        }
        return result;
    }
}

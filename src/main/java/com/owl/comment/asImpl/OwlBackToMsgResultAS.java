package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlBackToMsgResult;
import com.owl.comment.utils.AsLogUtil;
import com.owl.magicUtil.util.ObjectUtil;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/7.
 */
@Aspect
@Component
@Order(94)
public class OwlBackToMsgResultAS {


    @Pointcut("@annotation(com.owl.comment.annotations.OwlBackToMsgResult)")
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
        String codeName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).code();
        String msgName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).msg();
        String dataName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).data();
        String resultName = methodSignature.getMethod().getAnnotation(OwlBackToMsgResult.class).result();

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

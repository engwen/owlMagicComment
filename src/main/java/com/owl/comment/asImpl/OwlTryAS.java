package com.owl.comment.asImpl;


import com.owl.comment.annotations.OwlTry;
import com.owl.comment.utils.AsLogUtil;
import com.owl.util.RegexUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(190)
public class OwlTryAS {

    @Pointcut("@annotation(com.owl.comment.annotations.OwlTry)")
    public void setTryCut() {
    }

    @Around("setTryCut()")
    public Object tryCut(ProceedingJoinPoint joinPoint) throws Throwable {
        MsgResultVO result = new MsgResultVO();
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String value = methodSignature.getMethod().getAnnotation(OwlTry.class).value();
            if (!RegexUtil.isEmpty(value)) {
                AsLogUtil.error(joinPoint, value);
            }
            result.errorResult(MsgConstant.TRY_CATCH_THROWABLE_ERROR);
            e.printStackTrace();
        }
        return result;
    }
}

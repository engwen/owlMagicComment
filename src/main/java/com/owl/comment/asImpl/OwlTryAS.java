package com.owl.comment.asImpl;


import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(190)
public class OwlTryAS {
    private static Logger logger = Logger.getLogger(OwlTryAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.annotations.OwlTry)")
    public void setTryCut() {
    }

    @Around("setTryCut()")
    public Object tryCut(ProceedingJoinPoint joinPoint) throws Throwable {
        MsgResultVO result = new MsgResultVO();
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            result.errorResult(MsgConstant.REQUEST_CDUS_ERROR);
            e.printStackTrace();
        }
        return result;
    }
}

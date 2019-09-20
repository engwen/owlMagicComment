package com.owl.comment.utils;

import com.owl.util.DateCountUtil;
import com.owl.util.LogPrintUtil;
import org.aspectj.lang.JoinPoint;

import java.util.Date;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/6/25.
 */
public class AsLogUtil extends LogPrintUtil {

    public static void info(JoinPoint joinPoint, String msg) {
        log(joinPoint, msg, "INFO");
    }

    public static void error(JoinPoint joinPoint, String msg) {
        log(joinPoint, msg, "ERROR");
    }

    private static void log(JoinPoint joinPoint, String msg, String level) {
        System.out.println("[" + level + "][" + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMDHMS4BAR) +
                "][" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "] " + msg);
    }

}

package com.owl.comment.utils;

import com.owl.magicUtil.util.DateCountUtil;
import org.aspectj.lang.JoinPoint;

import java.util.Date;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/6/25.
 */
public class AsLogUtil {

    public static void info(JoinPoint joinPoint, String msg) {
        log(joinPoint, msg, "INFO");
    }

    public static void error(JoinPoint joinPoint, String msg) {
        log(joinPoint, msg, "ERROR");
    }

    public static void info(String msg) {
        log(msg, "INFO");
    }

    public static void error(String msg) {
        log(msg, "ERROR");
    }

    private static void log(JoinPoint joinPoint, String msg, String level) {
        System.out.println("[" + level + "][" + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMDHMS4BAR) +
                "][" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "] " + msg);
    }

    private static void log(String msg, String level) {
        System.out.println("[" + level + "][" + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMDHMS4BAR) +
                "][" + Thread.currentThread().getStackTrace()[3].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "] " + msg);
    }
}

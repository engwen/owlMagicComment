package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.magicUtil.util.ClassTypeUtil;
import com.owl.magicUtil.util.ObjectUtil;
import com.owl.magicUtil.util.RegexUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 參數注解功能實現
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/15.
 */
@Aspect
@Component
@Order(91)
public class OwlCheckParamsAS {
    private static Logger logger = Logger.getLogger(OwlCheckParamsAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.annotations.OwlCheckParams)")
    public void checkParamsCut() {
    }

    @Around("checkParamsCut()")
    public Object checkParams(ProceedingJoinPoint joinPoint) throws Throwable {
        MsgResultVO result = new MsgResultVO();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        獲取被標記不能爲空的屬性集合
        String[] notNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notNull();
        String[] notAllNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notAllNull();
//        其中是否存在空，默認不存在
        boolean hasNull = false;
        boolean allOrNull = true;
//        存放含有空的屬性
        List<String> paramsIsNull = new ArrayList<>();

        Object[] args = joinPoint.getArgs();
        if (args.length > 1) {
            logger.warn("本注解仅限使用对象或Map接收参数时使用");
            return joinPoint.proceed(joinPoint.getArgs());
        }

//        此處從requestHead頭中獲取參數，在请求开始的时候获取
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String[]> paramsHeadMap = request.getParameterMap();
        if (null != paramsHeadMap && paramsHeadMap.keySet().size() > 0) {
            for (String param : notNull) {
                if (null == paramsHeadMap.get(param) || paramsHeadMap.get(param).length == 0) {
                    paramsIsNull.add(param);
                    hasNull = true;
                }
            }
            for (String param : notAllNull) {
                if (null != paramsHeadMap.get(param) && paramsHeadMap.get(param).length > 0) {
                    allOrNull = false;
                    break;
                }
            }
        } else {
//          从接收封装的对象
            Map<String, Object> paramsBodyMap = new HashMap<>();
            Object paramsVO = args[0];
            if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                logger.debug("本注解仅限使用对象或Map接收参数时使用");
            } else {
//                使用Map接收参数
                if (paramsVO instanceof Map) {
                    paramsBodyMap = (Map<String, Object>) paramsVO;
                } else {
//                  使用对象接收参数
                    Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
                    for (Field field : fields) {
                        field.setAccessible(true);
                        paramsBodyMap.put(field.getName(), field.get(paramsVO));
                    }
                }
                for (String param : notNull) {
                    if (RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                        paramsIsNull.add(param);
                        hasNull = true;
                    }
                }
                for (String param : notAllNull) {
                    if (!RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                        allOrNull = false;
                        break;
                    }
                }
            }
        }
        if (hasNull) {
            logger.debug("请求参数错误");
            return result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能为空", paramsIsNull));
        } else if (notAllNull.length > 0 && allOrNull) {
            logger.debug("请求参数错误");
            return result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能全为空", Arrays.asList(notAllNull)));
        } else {
            logger.debug("参数校验成功");
            return joinPoint.proceed(joinPoint.getArgs());
        }
    }

    private static String backStr(String str, List arr) {
        String temp = arr.toString();
        return String.format(str, temp.substring(1, temp.length() - 1));
    }
}

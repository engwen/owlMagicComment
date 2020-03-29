package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.comment.utils.AsLogUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.ClassTypeUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;
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


//        此處從requestHead頭中獲取參數，@Param
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
        } else { // @RequestBody
            Object[] args = joinPoint.getArgs();
            if (args.length > 1) {
                AsLogUtil.error(joinPoint, "This annotation is limited to objects or Maps that receive parameters");
                return joinPoint.proceed(joinPoint.getArgs());
            }
            if (args.length == 0 && (notAllNull.length > 0 || notNull.length > 0)) {
                AsLogUtil.error(joinPoint, "parameters can`t be null");
                return MsgResultVO.getInstanceError(MsgConstant.REQUEST_PARAMETER_ERROR);
            }
//          从接收封装的对象
            Map<String, Object> paramsBodyMap = new HashMap<>();
            Object paramsVO = args[0];
            if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                AsLogUtil.error(joinPoint, "This annotation is limited to objects or Maps that receive parameters");
            } else {
                if (paramsVO instanceof List) {
                    Optional any = ((List) paramsVO).stream().filter(it -> checkListParams(it, notNull, notAllNull)).findAny();
                    if (any.isPresent()) {
                        return MsgResultVO.getInstanceError(MsgConstant.REQUEST_PARAMETER_ERROR);
                    } else {
                        return joinPoint.proceed(joinPoint.getArgs());
                    }
                }
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
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("request params %s can`t be null", paramsIsNull));
            AsLogUtil.error(joinPoint, result.getResultMsg());
            return result;
        } else if (notAllNull.length > 0 && allOrNull) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("request params %s can`t all be null", Arrays.asList(notAllNull)));
            AsLogUtil.error(joinPoint, result.getResultMsg());
            return result;
        } else {
            AsLogUtil.info(joinPoint, "Successful Params Check");
            return joinPoint.proceed(joinPoint.getArgs());
        }
    }

    private static String backStr(String str, List arr) {
        String temp = arr.toString();
        return String.format(str, temp.substring(1, temp.length() - 1));
    }

    /*
     * true 通过 false，不通过
     * @param paramsVO
     * @param notNull
     * @param notAllNull
     * @param <T>
     * @return
     * @throws Throwable
     */
    private static <T> boolean checkListParams(T paramsVO, String[] notNull, String[] notAllNull) {
        Map<String, Object> paramsBodyMap = new HashMap<>();
        boolean allOrNull = true;
        //                使用Map接收参数
        if (paramsVO instanceof Map) {
            paramsBodyMap = (Map<String, Object>) paramsVO;
        } else {
//                  使用对象接收参数
            Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    paramsBodyMap.put(field.getName(), field.get(paramsVO));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //存在为空的
        for (String param : notNull) {
            if (RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                return false;
            }
        }
        for (String param : notAllNull) {
            if (!RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                allOrNull = false;
                break;
            }
        }
        return notAllNull.length > 0 && !allOrNull;
    }
}

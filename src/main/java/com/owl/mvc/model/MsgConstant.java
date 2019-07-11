package com.owl.mvc.model;

import java.util.Locale;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/23.
 */
public class MsgConstant {
    private String code;
    private String msg;

    public static MsgConstant REQUEST_SUCCESS = new MsgConstant("0000", "请求成功");
    public static MsgConstant REQUEST_DEFAULT = new MsgConstant("0001", "默认返回数据");

    public static MsgConstant REQUEST_PARAMETER_ERROR = new MsgConstant("0002", "请求参数错误");
    public static MsgConstant REQUEST_NO_KNOW_ERROR = new MsgConstant("0003", "未知错误");
    public static MsgConstant REQUEST_NO_SIGNIN = new MsgConstant("0004", "用户未登录");
    public static MsgConstant REQUEST_PERMISSION_DEFINED = new MsgConstant("0005", "权限拒绝");
    public static MsgConstant REQUEST_DB_PERMISSION_DEFINED = new MsgConstant("0006", "底层权限拒绝");
    public static MsgConstant REQUEST_ACCOUNT_OR_PASSWORD_ERROR = new MsgConstant("0007", "账号或密码错误");
    public static MsgConstant REQUEST_NOT_EXITS = new MsgConstant("0008", "没有符合要求的数据");
    public static MsgConstant REQUEST_IS_EXITS = new MsgConstant("0009", "该数据已存在");
    public static MsgConstant REQUEST_CANT_UPDATE_ADMIN = new MsgConstant("0010", "管理员状态不可修改");
    public static MsgConstant REQUEST_METHOD_NOT_EXITS = new MsgConstant("0011", "该方法（接口）不存在，请检查代码");
    public static MsgConstant REQUEST_DB_ERROR = new MsgConstant("0012", "底层sql错误,请检查代码");
    public static MsgConstant REQUEST_BACK_ARE_LIST = new MsgConstant("0013", "符合条件的数据不唯一");
    public static MsgConstant REQUEST_CDUS_ERROR = new MsgConstant("0014", "操作失败");

    public static MsgConstant REQUEST_CANT_UPDATE_STATUS = new MsgConstant("0020", "更新操作不能进行删除操作");

    public static MsgConstant RESULT_DATA_TYPE_ERROR = new MsgConstant("0030", "返回参数类型错误");
    public static MsgConstant RESULT_NOT_SUPPORT_TYPE_ERROR = new MsgConstant("0031", "不支持此种类型封装的校验");

    public static MsgConstant PARAM_EMAIL_ERROR = new MsgConstant("1001", "邮箱格式错误");
    public static MsgConstant PARAM_MOBILE_ERROR = new MsgConstant("1002", "手机号格式错误");
    public static MsgConstant PARAM_ACCOUNT_ERROR = new MsgConstant("1003", "账户错误");
    public static MsgConstant SINGIN_ERROR = new MsgConstant("1004", "账号或密码错误错误");

    public static MsgConstant CONTROLLER_THROWABLE_ERROR = new MsgConstant("1005", "操作失败");
    public static MsgConstant TRY_CATCH_THROWABLE_ERROR = new MsgConstant("1006", "网站好像出了点小问题");

    public MsgConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

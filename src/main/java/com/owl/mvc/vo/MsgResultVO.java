package com.owl.mvc.vo;


import com.owl.magicUtil.model.ModelPrototype;
import com.owl.magicUtil.util.ObjectUtil;
import com.owl.mvc.model.MsgConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 基礎返回數據
 * author engwen
 * email xiachanzou@outlook.com
 * time 2017/10/23.
 */
public final class MsgResultVO<T> extends ModelPrototype {
    //添加將要返回的基礎數據
    private Boolean result;
    private String resultCode;
    private String resultMsg;

    //提供 汎型 对對象进行封装
    private T resultData;
    //儅以汎型數據仍不能滿足時，提供Map封裝參數
    private Map<String, Object> params;

    private void setMsgConstant(MsgConstant msgConstant) {
        this.resultCode = msgConstant.getCode();
        this.resultMsg = msgConstant.getMsg();
    }

    /*----------------------------  提供构造函数  --------------------------------*/
    public MsgResultVO() {
        this.result = true;
        this.params = new HashMap<>();
        setMsgConstant(MsgConstant.REQUEST_DEFAULT);
    }

    public MsgResultVO(Map<String, Object> params) {
        this.result = true;
        this.params = params;
        setMsgConstant(MsgConstant.REQUEST_DEFAULT);
    }

    public static <T> MsgResultVO<T> getInstanceSuccess() {
        return new MsgResultVO<T>().successResult();
    }

    public static <T> MsgResultVO<T> getInstanceSuccess(T t) {
        return new MsgResultVO<T>().successResult(t);
    }

    public static <T> MsgResultVO<T> getInstanceError(MsgConstant msgConstant) {
        return new MsgResultVO<T>().errorResult(msgConstant);
    }

    /*----------------------------  构造函数结束  --------------------------------*/

    /**
     * 請求失敗
     * @param msgConstant 枚举信息对象
     * @return 結果對象
     */
    public MsgResultVO<T> errorResult(MsgConstant msgConstant) {
        this.result = false;
        setMsgConstant(msgConstant);
        return this;
    }

    /**
     * 請求失敗
     * @param resultMsg 消息信息
     * @return 結果對象
     */
    public MsgResultVO<T> errorResult(String resultMsg) {
        this.result = false;
        this.resultCode = MsgConstant.REQUEST_NO_KNOW_ERROR.getCode();
        this.resultMsg = resultMsg;
        return this;
    }

    /**
     * 請求失敗
     * @param resultCode 消息代碼
     * @param resultMsg  消息信息
     * @return 結果對象
     */
    public MsgResultVO<T> errorResult(String resultCode, String resultMsg) {
        this.result = false;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }

    /**
     * 請求失敗
     * @param prototype   對象
     * @param msgConstant 枚举信息对象
     * @return 結果對象
     */
    public MsgResultVO<T> errorResult(T prototype, MsgConstant msgConstant) {
        this.result = false;
        this.resultData = prototype;
        setMsgConstant(msgConstant);
        return this;
    }

    /**
     * 請求失敗
     * @param prototype  對象
     * @param resultCode 消息代碼
     * @param resultMsg  消息信息
     * @return 結果對象
     */
    public MsgResultVO<T> errorResult(T prototype, String resultCode, String resultMsg) {
        this.result = false;
        this.resultData = prototype;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }

    /**
     * 請求成功
     * @return 結果對象
     */
    public MsgResultVO<T> successResult() {
        this.result = true;
        setMsgConstant(MsgConstant.REQUEST_SUCCESS);
        return this;
    }

    /**
     * 請求成功
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(String resultMsg) {
        this.result = true;
        this.resultCode = MsgConstant.REQUEST_DEFAULT.getCode();
        this.resultMsg = resultMsg;
        return this;
    }


    /**
     * 請求成功
     * @param resultCode 消息代碼
     * @param resultMsg  消息信息
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(String resultCode, String resultMsg) {
        this.result = true;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }

    /**
     * 請求成功
     * @param msgConstant 枚举信息对象
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(MsgConstant msgConstant) {
        this.result = true;
        this.resultCode = msgConstant.getCode();
        this.resultMsg = msgConstant.getMsg();
        return this;
    }

    /**
     * 請求成功
     * @param prototype 汎型對象
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(T prototype) {
        this.result = true;
        this.resultData = prototype;
        setMsgConstant(MsgConstant.REQUEST_SUCCESS);
        return this;
    }

    /**
     * 請求成功
     * @param msgConstant 枚举信息对象
     * @param prototype   汎型對象
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(T prototype, MsgConstant msgConstant) {
        this.result = true;
        this.resultData = prototype;
        setMsgConstant(msgConstant);
        return this;
    }

    /**
     * 請求成功
     * @param prototype  汎型對象
     * @param resultCode 消息代碼
     * @param resultMsg  消息信息
     * @return 結果對象
     */
    public MsgResultVO<T> successResult(T prototype, String resultCode, String resultMsg) {
        this.result = true;
        this.resultData = prototype;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        return this;
    }

    /**
     * 將本類中的結果信息傳遞給其他的msg
     * @param anotherMsg 另一個msgResult
     * @return ModelPrototype 返回原來的引用
     */
    public MsgResultVO setThisMsgToAnotherMsg(MsgResultVO anotherMsg) {
        anotherMsg.setResult(this.result);
        anotherMsg.setResultCode(this.resultCode);
        anotherMsg.setResultMsg(this.resultMsg);
        return anotherMsg;
    }

    /**
     * 將其他msg的結果信息傳遞給本類
     * @param anotherMsg 另一個msgResult
     */
    public void getMsgByAnotherMsg(MsgResultVO anotherMsg) {
        this.result = anotherMsg.result;
        this.resultCode = anotherMsg.resultCode;
        this.resultMsg = anotherMsg.resultMsg;
    }

    /**
     * 向Map中传递参数名以及值
     * @param key   参数名称
     * @param value 值
     */
    public void setParam(String key, Object value) {
        this.params.put(key, value);
    }

    /**
     * 获取指定key的值
     * @param key 参数名称
     * @return obj
     */
    public Object getParamValue(String key) {
        return this.params.get(key);
    }

    /**
     * 移除Map中的參數
     * @param key 參數名
     */
    public void removeParam(String key) {
        this.params.remove(key);
    }

    /**
     * 生成可直接返回的目標對象，剝除多餘的對象信息
     * @return 目标对象
     */
    public MsgResultVO aimMsg() {
        return this.getResult() ? this : this.setThisMsgToAnotherMsg(new MsgResultVO());
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

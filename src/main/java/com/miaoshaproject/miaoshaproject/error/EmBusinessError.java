package com.miaoshaproject.miaoshaproject.error;

import com.miaoshaproject.miaoshaproject.response.CommonReturenType;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/2/28 02:34
 * @Description:
 */
public enum EmBusinessError implements CommonError {

    /**
     * 通用错误类型 一般是以10001开头的
     * 举例：比如密码格式不合法啊，参数有误啊， 都可以通过 通用方法去覆盖00001的信息setErrorMsg（）
     */
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),


    UNKNOWN_ERROR(10002,"未知错误"),
    /**x
     * 20000开头为用户信息相关错误定义
     */
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登陆不能下单"),

    /**
     * 30000开头是以交易信息错误
     */
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;

    private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode ;
    private String errMsg;

    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
       this.errMsg = errMsg;
       return this;
    }
}

package com.miaoshaproject.miaoshaproject.error;

import java.time.temporal.Temporal;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/2/28 03:08
 * @Description:
 */
//使用的设计模式是 包装器业务异常类实现
public class BusinessException extends Exception implements CommonError{
    private CommonError commonError;

    //直接接收emBusinessError的参数用与构造业务异常
    public BusinessException (CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接受自定义errorMsg的方式构造业务异常
    public BusinessException (CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
       this.commonError.setErrorMsg(errMsg);
        return this;
    }
}

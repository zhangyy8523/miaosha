package com.miaoshaproject.miaoshaproject.controller;

import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.response.CommonReturenType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/2/28 03:52
 * @Description:
 */
public class BaseController {

    public  static final String CONTENT_FORM_FORMED="application/x-www-form-urlencoded";


    //定义excetionHandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){

        Map<String,Object> respMap = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException =(BusinessException)ex; //强转获取异常信息
            respMap.put("errCode",businessException.getErrorCode());
            respMap.put("errMsg",businessException.getErrorMsg());
        }else{
            respMap.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            respMap.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }

        return CommonReturenType.create(respMap,"fail");



    }
}

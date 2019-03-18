package com.miaoshaproject.miaoshaproject.controller;

import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.response.CommonReturenType;
import com.miaoshaproject.miaoshaproject.service.OrderInfoService;
import com.miaoshaproject.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.miaoshaproject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/14 11:20
 * @Description:
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     *  下单请求
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/createOrder",method =RequestMethod.POST ,consumes = {CONTENT_FORM_FORMED})
    @ResponseBody
    public CommonReturenType createOrder(@RequestParam String itemId,
                                         @RequestParam Integer amount
    ) throws BusinessException {
        Boolean islogin = (Boolean) httpServletRequest.getSession().getAttribute("LOGIN");
        if (islogin==null ||!islogin.booleanValue()){
            throw  new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        //获取用户登录的信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderInfoServiceOrder = orderInfoService.createOrder(userModel.getId(), itemId, amount);

        return CommonReturenType.create(null);
    }
}

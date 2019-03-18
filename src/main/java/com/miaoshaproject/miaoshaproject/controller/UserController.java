package com.miaoshaproject.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.miaoshaproject.controller.viewObject.UserVo;
import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.response.CommonReturenType;
import com.miaoshaproject.miaoshaproject.service.UserService;
import com.miaoshaproject.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * @Auther: zhangyuyang
 * @Date: 2019/1/16 16:13
 * @Description:
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController  extends  BaseController{


    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    /**
     *
     * 功能描述: 用户注册
     * @auther: zhangyuyang
     * @date: 2019/3/8 9:12 AM
     * @param:
     * @return:
     */
    @RequestMapping(value = "/register",method =RequestMethod.POST ,consumes = {CONTENT_FORM_FORMED})
    @ResponseBody
    public CommonReturenType register(@RequestParam String telphone ,
                                      @RequestParam String otpCode,
                                      @RequestParam String name ,
                                      @RequestParam Integer gender ,
                                      @RequestParam String password ,
                                      @RequestParam Integer age) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpcode相符合
        String  sessionOtpCode = (String) this.request.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,sessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(encodeByMD5(password));
        userService.register(userModel);
        return CommonReturenType.create(null);
    }

    /**
     *
     * 功能描述:用户获取otp短信接口
     * @auther: zhangyuyang
     * @date: 2019/3/6 11:08 PM
     * @param:
     * @return:
     */
    @RequestMapping(value = "/getOtp",method =RequestMethod.POST ,consumes = {CONTENT_FORM_FORMED})
    @ResponseBody
    public CommonReturenType getOtp(@RequestParam String phone ){
        //需要按照一定的规则生成OPT验证码
        Random random = new Random();
        int nextInt = random.nextInt(99999);
        nextInt+=10000;
        String otpCode = String.valueOf(nextInt);

        //将OPT验证码用对应的手机号相关联，暂时使用httpsession的方式绑定手机号与code（建议用redis存放)
        request.getSession().setAttribute(phone,otpCode);
        //讲OPT验证码通过短信通道发送给用户（省略）
        System.out.println("telPhone:"+phone+"&& otpCode:"+otpCode);
        return CommonReturenType.create(null);
    }

    /**
     *
     * 功能描述:   功能代码测试（可以忽略)
     * @auther: zhangyuyang
     * @date: 2019/3/6 11:08 PM
     * @param:
     * @return:
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturenType getUser(@RequestParam(name = "id") String id) throws Exception {
        //调用service服务获取对应的数据 并返回给前端

        UserModel user = userService.getUser(id);

        //若获取的用户信息不存在
        if (user == null){
            //throw  new BusinessException(EmBusinessError.USER_NOT_EXIST);
            user.setEncrptPassword(null);
        }

        //讲核心领域模型用户对象转化为可供UI使用的viewObject
        UserVo userVo = convertFromModel(user);

        return CommonReturenType.create(userVo);
    }

    private UserVo convertFromModel(UserModel model){
        if (model == null){
            return null;
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(model,vo);
        return vo;
    }

    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 =MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String s = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return s;

    }

    ////定义excetionHandler解决未被controller层吸收的exception
    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    //public Object handlerException(HttpServletRequest request,Exception ex){
    //
    //    Map<String,Object> respMap = new HashMap<>();
    //    if (ex instanceof BusinessException){
    //        BusinessException businessException =(BusinessException)ex; //强转获取异常信息
    //        respMap.put("errCode",businessException.getErrorCode());
    //        respMap.put("errMsg",businessException.getErrorMsg());
    //    }else{
    //        respMap.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrorCode());
    //        respMap.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
    //    }
    //
    //    return CommonReturenType.create(respMap,"fail");
    //
    //
    //
    //}

    /**
     *
     * 功能描述: 登录功能
     * @auther: zhangyuyang
     * @date: 2019/3/9 8:28 AM
     * @param: 
     * @return: 
     */
    @RequestMapping(value = "/login",method =RequestMethod.POST ,consumes = {CONTENT_FORM_FORMED})
    @ResponseBody
    public CommonReturenType login(@RequestParam String telphone , @RequestParam String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) || org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.encodeByMD5(password));
        //加入到用户成功的session中
        this.request.getSession().setAttribute("LOGIN",true);
        this.request.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturenType.create(null);
    }




}

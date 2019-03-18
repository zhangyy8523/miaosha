package com.miaoshaproject.miaoshaproject.service;

import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.service.model.UserModel;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/1/16 16:17
 * @Description:
 */

public interface UserService {
    UserModel getUser(String id);

    Integer register(UserModel model) throws BusinessException;

    UserModel validateLogin(String telphone, String password) throws BusinessException;
}

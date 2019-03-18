package com.miaoshaproject.miaoshaproject.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.miaoshaproject.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.service.UserService;
import com.miaoshaproject.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/1/16 16:17
 * @Description:
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUser(String id) {
        //调用userdomapper获取对应的用户数据
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO==null){
            return null;
        }
        //通过用户id获取用户加密的密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public Integer register(UserModel model) throws BusinessException {
        String randomUUID = RandomUtil.randomUUID();
        model.setId(randomUUID);
        if (model == null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //if (StringUtils.isEmpty(model.getName()) || model.getAge() == null || model.getGender() == null
        //        || StringUtils.isEmpty(model.getTelphone())) {
        //    throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        //}

        //使用新的校验方式 validator
        ValidationResult validationResult  = validator.validate(model);
        if (validationResult.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }


        //实现model 转成 dataObject的方法
        try {
            UserDO userDO = convertFromModel(model);
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException e){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已存在");
        }

        UserPasswordDO userPasswordDO = convertFromPasswordDOModel(model);
        if (userPasswordDOMapper.insertSelective(userPasswordDO)>0){
            return 1;
        }else{
            return 0;
        }

    }

    @Override
    public UserModel validateLogin(String telphone, String password) throws BusinessException {


        //通过用户手机获取用户的信息
        UserDO userDO = userDOMapper.selectBytelPhone(telphone);
        if (null == userDO){
            throw  new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if (!StringUtils.equals(password,userModel.getEncrptPassword())){
            throw  new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserDO convertFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        userDO.setId(userModel.getId());
        return userDO;
    }

    private UserPasswordDO convertFromPasswordDOModel(UserModel userModel ){
        if (userModel==null){
            return null;
        }

        String randomUUID = RandomUtil.randomUUID();
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setId(randomUUID);
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }



    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if (userDO ==null){
            return  null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);

        if (userPasswordDO!=null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }


        return userModel;
    }
}

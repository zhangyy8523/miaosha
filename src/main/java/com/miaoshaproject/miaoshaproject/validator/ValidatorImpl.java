package com.miaoshaproject.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 09:12
 * @Description:
 */
//声明他是spring的一个bean
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;



    /**
     *  spring 初始化后完成后，会回调afterPropertiesSet
     * 功能描述: 实例化校验器
     * @auther: zhangyuyang
     * @date: 2019/3/11 9:14 AM
     * @param:
     * @return:
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //讲 hibernate validator通过工程初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    //实现效验方法并返回效验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        //如果bean参数违背了validation定义的规则 则返回结果validate
        Set<ConstraintViolation<Object>> validate = validator.validate(bean);
        if (validate.size()>0){ //如果大于零 则有异常 有错误
            //有错误
            result.setHasErrors(true);
            //拉姆达表达式 1.8的新特性
            validate.forEach(constraintViolation ->{
                    String errMsg = constraintViolation.getMessage();//获取错误信息
                    String propertyName = constraintViolation.getPropertyPath().toString(); // 获取知道是哪个字段错误了
                   String put = result.getErrorMsgMap().put(propertyName, errMsg);//key 是名称 errmsg 错误信息
            });
        }
        return result;
    }
}

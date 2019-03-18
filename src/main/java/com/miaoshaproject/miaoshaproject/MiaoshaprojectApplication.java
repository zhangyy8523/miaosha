package com.miaoshaproject.miaoshaproject;

import com.miaoshaproject.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.miaoshaproject.miaoshaproject"})
@RestController
@MapperScan("com.miaoshaproject.miaoshaproject.dao")
public class MiaoshaprojectApplication {

    /****只做调试使用**/
    @Autowired
    private UserDOMapper userDOMapper;

    //@RequestMapping("/")
    //public String home(){
    //    //UserDO userDO = userDOMapper.selectByPrimaryKey();
    //    if (userDO==null){
    //        return "用户对应不存在";
    //    }else {
    //        return "hello word  "+userDO.getName();
    //    }
    //
    //}

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaprojectApplication.class, args);
    }

}


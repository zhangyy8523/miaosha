package com.miaoshaproject.miaoshaproject.controller.viewObject;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/1/21 23:32
 * @Description:
 */
public class UserVo {
    private String id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telphone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}

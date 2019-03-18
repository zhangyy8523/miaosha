package com.miaoshaproject.miaoshaproject.error;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/2/28 02:29
 * @Description:
 */
public interface CommonError {
    /**
     *
     * 功能描述: 获取错误码
     * @auther: zhangyuyang
     * @date: 2019/2/28 2:30 AM
     * @param:
     * @return:
     */
    public int getErrorCode ();
    /**
     *
     * 功能描述:    获取错误信息
     * @auther: zhangyuyang
     * @date: 2019/2/28 2:31 AM
     * @param:
     * @return:
     */
    public String getErrorMsg();

    public CommonError setErrorMsg(String errMsg);

}

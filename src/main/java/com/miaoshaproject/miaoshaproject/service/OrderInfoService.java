package com.miaoshaproject.miaoshaproject.service;

import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.service.model.OrderModel;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/13 14:17
 * @Description:
 */

public interface OrderInfoService {
    /**
     * 创建交易订单
     * @param userId
     * @param itemId
     * @param amount
     * @return
     */
    OrderModel createOrder(String userId,String itemId,Integer amount) throws BusinessException;





}

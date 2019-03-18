package com.miaoshaproject.miaoshaproject.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.miaoshaproject.miaoshaproject.dao.OrderInfoVOMapper;
import com.miaoshaproject.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.miaoshaproject.dataobject.OrderInfoVO;
import com.miaoshaproject.miaoshaproject.dataobject.SequenceDO;
import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.service.ItemService;
import com.miaoshaproject.miaoshaproject.service.OrderInfoService;
import com.miaoshaproject.miaoshaproject.service.UserService;
import com.miaoshaproject.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/13 14:19
 * @Description:
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    OrderInfoVOMapper orderInfoVOMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(String userId, String itemId, Integer amount) throws BusinessException {
        //1.效验下单的状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel user = userService.getUser(userId);
        if (user == null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if (amount<=0 || amount>99){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息有误");
        }

        //2.落单减库存或者支付减库存两种 （锁库存)
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result){
            throw  new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setUserId(userId);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        //生成交易流水号（订单号）
        orderModel.setId(generateOrderNo());
        OrderInfoVO orderInfoVO = this.convertFromOrderModel(orderModel);
        orderInfoVOMapper.insert(orderInfoVO);
        itemService.increseSales(itemId,amount); //商品销量增加
        //4.返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) //不管成功与否 我的事务都会提交
    private String generateOrderNo(){
        StringBuilder stringBuffer = new StringBuilder();
        //订单号有十六位 前八位为时间信息包含年月日
        LocalDateTime now =  LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuffer.append(nowDate);

        //中间六位为自增序列
        //获取当前sequcese
        Integer currentValue = 0;
        SequenceDO sequenceDo = sequenceDOMapper.getSequenceByName("order_info");
        currentValue = sequenceDo.getCurrentValue();
        sequenceDo.setCurrentValue(sequenceDo.getCurrentValue()+sequenceDo.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDo);
        String sequenceStr = String.valueOf(currentValue);
        for (int i=0;i< 6- sequenceStr.length();i++){ //还差几位
            stringBuffer.append(0);
        }
        stringBuffer.append(sequenceStr);
        //后两位为分库分表位,暂时写死
        stringBuffer.append("00");
        return stringBuffer.toString();
    }

    private OrderInfoVO convertFromOrderModel(OrderModel orderModel){
        if (null==orderModel){
            return null;
        }
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        BeanUtils.copyProperties(orderModel,orderInfoVO);
        orderInfoVO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderInfoVO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderInfoVO;
    }
}

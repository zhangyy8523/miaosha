package com.miaoshaproject.miaoshaproject.service.model;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/13 10:02
 * @Description:
 */

import java.math.BigDecimal;

/**
 *用户下单的交易模型
 */
public class OrderModel {
    //20181021000012828
    //交易号
    private String id;
    //购买的用户id
    private String userId;
    //商品id
    private String itemId;
    //商品的单价
    private BigDecimal itemPrice ;
    //购买的数量
    private Integer amount ;
    //购买的金额
    private BigDecimal orderPrice ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}

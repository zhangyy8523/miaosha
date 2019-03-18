package com.miaoshaproject.miaoshaproject.service;

import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.service.model.ItemModel;

import java.util.List;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 11:03
 * @Description:
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> getlistItem();

    //商品详情浏览
    ItemModel getItemById(String id);

    //库存扣减
    boolean decreaseStock(String itemId,Integer amount);

    //商品销量增加
    void increseSales(String itemId,Integer amount);

}

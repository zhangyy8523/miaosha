package com.miaoshaproject.miaoshaproject.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.miaoshaproject.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.miaoshaproject.dataobject.ItemDO;
import com.miaoshaproject.miaoshaproject.dataobject.ItemDOExample;
import com.miaoshaproject.miaoshaproject.dataobject.ItemStockDO;
import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.miaoshaproject.service.ItemService;
import com.miaoshaproject.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.miaoshaproject.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 11:07
 * @Description:
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        String randomUUID = RandomUtil.randomUUID();
        //校验入参
        ValidationResult validationResult  = validator.validate(itemModel);
        if (validationResult.isHasErrors()){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }
        //转换ItemModel -->dataObject
        itemModel.setId(randomUUID);
        ItemDO itemDO = convertItemDOFromItemModel(itemModel);

        //写入数据库
        if (itemDOMapper.insertSelective(itemDO)>0){
            ItemStockDO itemStockDO = converItemStockFromItemModel(itemModel);
            itemStockDOMapper.insertSelective(itemStockDO);
        }

        //返回创建完成的对象
        return this.getItemById(randomUUID);
    }

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return  null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO converItemStockFromItemModel(ItemModel itemModel){

        if (itemModel ==null){
            return  null;
        }
        String randomUUID = RandomUtil.randomUUID();
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        itemStockDO.setId(randomUUID);
        return itemStockDO;
    }



    @Override
    public List<ItemModel> getlistItem() {
        ItemDOExample itemDOExample = new ItemDOExample();
        itemDOExample.setOrderByClause("sales desc");
        List<ItemDO> itemDOS = itemDOMapper.selectByExample(itemDOExample);

        List<ItemModel> collect = itemDOS.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ItemModel getItemById(String id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);

        if (itemDO == null){
            return null;
        }
        //操作活动库存的数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);

        //将dataObject > model
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
        return itemModel;
    }
    private  ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO ){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }


    @Override
    @Transactional
    public boolean decreaseStock(String itemId, Integer amount) {
        //添加行级锁
        int decreaseStock = itemStockDOMapper.decreaseStock(amount, itemId);
        if (decreaseStock>0){
            //更新库存成功
            return true;
        }else{
            return false;
        }



    }

    @Override
    @Transactional
    public void increseSales(String itemId, Integer amount) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
        if (null !=itemDO ){
            itemDO.setSales(itemDO.getSales()+amount);
            itemDOMapper.updateByPrimaryKey(itemDO);
        }
    }


}

package com.miaoshaproject.miaoshaproject.controller;

import com.miaoshaproject.miaoshaproject.controller.viewObject.ItemVo;
import com.miaoshaproject.miaoshaproject.error.BusinessException;
import com.miaoshaproject.miaoshaproject.response.CommonReturenType;
import com.miaoshaproject.miaoshaproject.service.ItemService;
import com.miaoshaproject.miaoshaproject.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 13:22
 * @Description:
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    /**
     *
     * 功能描述:创建商品
     * @auther: zhangyuyang
     * @date: 2019/3/11 10:28 PM
     * @param:
     * @return:
     */
    @RequestMapping(value = "/create",method =RequestMethod.POST ,consumes = {CONTENT_FORM_FORMED})
    @ResponseBody
    public CommonReturenType createItem(@RequestParam String title,
                                        @RequestParam String description,
                                        @RequestParam String imgUrl,
                                        @RequestParam Integer stock,
                                        @RequestParam BigDecimal price) throws BusinessException {

        //封装service请求用来创建商品
        ItemModel itemModel= new ItemModel();
        itemModel.setStock(stock);
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        itemModel.setPrice(price);

        ItemModel item = itemService.createItem(itemModel);

        return  CommonReturenType.create(convertVoFromItemModel(item));
    }

    private ItemVo convertVoFromItemModel(ItemModel itemModel ){
        ItemVo itemVo = new ItemVo();
        if (itemModel == null){
            return null;
        }
        BeanUtils.copyProperties(itemModel,itemVo);

        return itemVo;
    }

    /**
     *
     * 功能描述: 商品详情页浏览
     * @auther: zhangyuyang
     * @date: 2019/3/11 10:30 PM
     * @param: 
     * @return: 
     */
    @RequestMapping(value = "/get",method =RequestMethod.GET )
    @ResponseBody
    public CommonReturenType getItem(@RequestParam String id){
        ItemModel itemModel  = itemService.getItemById(id);
        ItemVo itemVo = convertVoFromItemModel(itemModel);
        return CommonReturenType.create(itemVo);
    }


    /**
     *
     * 功能描述: 商品列表页面浏览
     * @auther: zhangyuyang
     * @date: 2019/3/12 1:25 PM
     * @param:
     * @return:
     */
    @RequestMapping(value = "/list",method =RequestMethod.GET )
    @ResponseBody
    public CommonReturenType getList(){
        List<ItemModel> itemModels = itemService.getlistItem();
        //使用steam api将list内的itemModel转化为ItemVo
        List<ItemVo> itemVoList = itemModels.stream().map(itemModel -> {
            ItemVo itemVo = this.convertVoFromItemModel(itemModel);
            return itemVo;
        }).collect(Collectors.toList());
        return CommonReturenType.create(itemVoList);
    }

}

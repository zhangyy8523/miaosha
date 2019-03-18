package com.miaoshaproject.miaoshaproject.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 10:40
 * @Description:
 */
public class ItemModel {

    private String id;
    //商品名称
    @NotBlank(message = "商品名称不能为空")
    private String title ;

    //商品价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price ;

   //商品的库存
    @NotNull(message = "库存不能不填")
    private Integer stock  ;

    //商品的描述
    @NotBlank(message = "商品的描述不能为空")
    private String description;

    //商品的销量
    private Integer sales = 0;

    //商品的描述图片的url
    @NotBlank(message = "商品的描述图片信息不能为空")
    private String imgUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

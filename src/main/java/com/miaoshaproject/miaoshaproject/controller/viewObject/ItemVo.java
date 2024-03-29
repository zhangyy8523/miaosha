package com.miaoshaproject.miaoshaproject.controller.viewObject;


import java.math.BigDecimal;
/**
 * @Auther: zhangyuyang
 * @Date: 2019/3/11 13:23
 * @Description:
 */
public class ItemVo {
    private String id;
    //商品名称
    private String title ;

    //商品价格
    private BigDecimal price ;

    //商品的库存
    private Integer stock  ;

    //商品的描述
    private String description;

    //商品的销量
    private Integer sales;

    //商品的描述图片的url
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

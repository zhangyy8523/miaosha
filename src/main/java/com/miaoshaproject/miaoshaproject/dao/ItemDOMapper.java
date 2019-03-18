package com.miaoshaproject.miaoshaproject.dao;

import com.miaoshaproject.miaoshaproject.dataobject.ItemDO;
import com.miaoshaproject.miaoshaproject.dataobject.ItemDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    long countByExample(ItemDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int deleteByExample(ItemDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int insert(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int insertSelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    List<ItemDO> selectByExample(ItemDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    ItemDO selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int updateByExampleSelective(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int updateByExample(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int updateByPrimaryKeySelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Mon Mar 11 22:24:59 CST 2019
     */
    int updateByPrimaryKey(ItemDO record);
}
package com.entity.model;

import com.entity.GoodsInEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 入库
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class GoodsInModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 商品
     */
    private Integer goodsId;


    /**
     * 供应商
     */
    private Integer gongyinghsangId;


    /**
     * 操作人
     */
    private Integer yuangongId;


    /**
     * 单号
     */
    private String goodsInDanhaoNumber;


    /**
     * 入库数量
     */
    private Integer goodsInNumber;


    /**
     * 入库时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 备注
     */
    private String goodsInContent;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：商品
	 */
    public Integer getGoodsId() {
        return goodsId;
    }


    /**
	 * 设置：商品
	 */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    /**
	 * 获取：供应商
	 */
    public Integer getGongyinghsangId() {
        return gongyinghsangId;
    }


    /**
	 * 设置：供应商
	 */
    public void setGongyinghsangId(Integer gongyinghsangId) {
        this.gongyinghsangId = gongyinghsangId;
    }
    /**
	 * 获取：操作人
	 */
    public Integer getYuangongId() {
        return yuangongId;
    }


    /**
	 * 设置：操作人
	 */
    public void setYuangongId(Integer yuangongId) {
        this.yuangongId = yuangongId;
    }
    /**
	 * 获取：单号
	 */
    public String getGoodsInDanhaoNumber() {
        return goodsInDanhaoNumber;
    }


    /**
	 * 设置：单号
	 */
    public void setGoodsInDanhaoNumber(String goodsInDanhaoNumber) {
        this.goodsInDanhaoNumber = goodsInDanhaoNumber;
    }
    /**
	 * 获取：入库数量
	 */
    public Integer getGoodsInNumber() {
        return goodsInNumber;
    }


    /**
	 * 设置：入库数量
	 */
    public void setGoodsInNumber(Integer goodsInNumber) {
        this.goodsInNumber = goodsInNumber;
    }
    /**
	 * 获取：入库时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：入库时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：备注
	 */
    public String getGoodsInContent() {
        return goodsInContent;
    }


    /**
	 * 设置：备注
	 */
    public void setGoodsInContent(String goodsInContent) {
        this.goodsInContent = goodsInContent;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }

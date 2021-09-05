package com.cjl.watersystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Data
@TableName(value = "shop")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID=1L;

    @TableId(value = "shop_id", type = IdType.INPUT)
    private String shopId;

    private String shopName;

    private Integer waterReserve;

    private Integer dispenserReserve;


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getWaterReserve() {
        return waterReserve;
    }

    public void setWaterReserve(Integer waterReserve) {
        this.waterReserve = waterReserve;
    }

    public Integer getDispenserReserve() {
        return dispenserReserve;
    }

    public void setDispenserReserve(Integer dispenserReserve) {
        this.dispenserReserve = dispenserReserve;
    }

    @Override
    public String toString() {
        return "Shop{" +
        "shopId=" + shopId +
        ", shopName=" + shopName +
        ", waterReserve=" + waterReserve +
        ", dispenserReserve=" + dispenserReserve +
        "}";
    }
}

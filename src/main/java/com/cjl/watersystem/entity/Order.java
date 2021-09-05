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
@TableName(value = "order")
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

    @TableId(value = "order_id", type = IdType.INPUT)
    private String orderId;

    private String customerId;

    private Float amount;

    private Integer state;

    private String date;

    private String waterId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWaterId() {
        return waterId;
    }

    public void setWaterId(String waterId) {
        this.waterId = waterId;
    }

    @Override
    public String toString() {
        return "Order{" +
        "orderId=" + orderId +
        ", customerId=" + customerId +
        ", amount=" + amount +
        ", state=" + state +
        ", date=" + date +
        ", waterId=" + waterId +
        "}";
    }
}

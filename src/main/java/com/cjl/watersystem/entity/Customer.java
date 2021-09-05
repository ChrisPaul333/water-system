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
@TableName(value = "customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID=1L;

    @TableId(value = "customer_id", type = IdType.INPUT)
    private String customerId;

    private String customerName;

    private String phone;

    private String address;

    private Integer quantity;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Customer{" +
        "customerId=" + customerId +
        ", customerName=" + customerName +
        ", phone=" + phone +
        ", address=" + address +
        ", quantity=" + quantity +
        "}";
    }
}

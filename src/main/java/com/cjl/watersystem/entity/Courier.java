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
@TableName(value = "courier")
public class Courier extends Model<Courier> {

    private static final long serialVersionUID=1L;

    @TableId(value = "courier_id", type = IdType.INPUT)
    private String courierId;

    private String courierName;

    private Integer counts;

    private Integer state;

    private String phone;


    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Courier{" +
        "courierId=" + courierId +
        ", courierName=" + courierName +
        ", counts=" + counts +
        ", state=" + state +
        ", phone=" + phone +
        "}";
    }
}

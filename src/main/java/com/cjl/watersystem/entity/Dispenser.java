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
@TableName(value = "dispenser")
public class Dispenser extends Model<Dispenser> {

    private static final long serialVersionUID=1L;

    @TableId(value = "dispenser_id", type = IdType.INPUT)
    private String dispenserId;

    private String manufacturingDate;

    private Integer state;


    public String getDispenserId() {
        return dispenserId;
    }

    public void setDispenserId(String dispenserId) {
        this.dispenserId = dispenserId;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Dispenser{" +
        "dispenserId=" + dispenserId +
        ", manufacturingDate=" + manufacturingDate +
        ", state=" + state +
        "}";
    }
}

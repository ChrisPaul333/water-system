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
@TableName(value = "admin")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID=1L;

    @TableId(value = "admin_id", type = IdType.INPUT)
    private String adminId;

    private String adminPwd;


    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    @Override
    public String toString() {
        return "Admin{" +
        "adminId=" + adminId +
        ", adminPwd=" + adminPwd +
        "}";
    }
}

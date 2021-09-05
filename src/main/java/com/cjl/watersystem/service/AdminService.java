package com.cjl.watersystem.service;

import com.cjl.watersystem.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
public interface AdminService extends IService<Admin> {
//        public String checkId(String adminId);
        public String checkLogin(Admin loginAdmin);
}

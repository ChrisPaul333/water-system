package com.cjl.watersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjl.watersystem.entity.Admin;
import com.cjl.watersystem.mapper.AdminMapper;
import com.cjl.watersystem.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Service
public class AdminImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private  AdminMapper adminMapper;

//    public String checkId(String adminId){
//        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("admin_id",adminId);
//        Admin admin = adminMapper.selectOne(queryWrapper);
//        if(admin == null){
//            return "101"; //用户不存在
//        } else {
//            return "102"; //用户已存在
//        }
//    }

    public String checkLogin(Admin loginAdmin){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",loginAdmin.getAdminId());
        Admin admin = adminMapper.selectOne(queryWrapper);
        if(admin == null){
            return "100"; //用户不存在
        } else {
            if(loginAdmin.getAdminPwd().equals(admin.getAdminPwd())){
                return "200"; //密码正确
            } else {
                return "300"; //密码不正确
            }
        }
    }
}

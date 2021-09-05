package com.cjl.watersystem.controller;


import com.cjl.watersystem.entity.Admin;
import com.cjl.watersystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //验证用户名是否存在
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String adminLogin(@RequestBody Map<String,String> loginAdmin){
        String id = loginAdmin.get("account");
        String password = loginAdmin.get("password");
        Admin admin = new Admin();
        admin.setAdminId(id);
        admin.setAdminPwd(password);
        String check = adminService.checkLogin(admin);
        if(check.equals("100")){
            return "account not exists";
        } else if(check.equals("200")){
            return "success";
        } else {
            return "error password";
        }
    }
}


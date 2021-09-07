package com.cjl.watersystem.controller;


import com.cjl.watersystem.entity.Admin;
import com.cjl.watersystem.service.*;
import com.cjl.watersystem.util.DataJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.cjl.watersystem.CONSTANT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private WaterService waterService;
    @Autowired
    private DispenserService dispenserService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CourierService courierService;
    //验证用户名是否存在
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
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

    /*
    * 首页信息
    * */
    @RequestMapping("/statistics")
    @ResponseBody
    public String getStatistics(){
        Map<String, Integer> map = new HashMap<>();
        int waterNum = waterService.count();
        int dispenserNum = dispenserService.count();
        int days = (int) ((new Date().getTime() - CONSTANT.ORIGIN_TIME.getTime()) / (24*60*60*1000));
        int customerNum = customerService.count();
        int courierNum = courierService.count();
        map.put("waterNum",waterNum);
        map.put("dispenserNum",dispenserNum);
        map.put("customerNum",customerNum);
        map.put("courierNum",courierNum);
        map.put("days",days);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        dataJsonUtils.setCode(200);
        dataJsonUtils.setData(map);
        return dataJsonUtils.toString();
    }

}


package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Courier;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.service.CourierService;
import com.cjl.watersystem.util.DataJsonUtils;
import com.cjl.watersystem.util.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/courier")
public class CourierController {
    @Autowired
    private CourierService courierService;

    /*
    * 获取所有送水员信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
    public String getCourierList(@Param("page") int page, @Param("limit") int limit, @Param("courier_id") String courier_id, @Param("courier_name") String courier_name,
                                 @Param("count") String count, @Param("state") String state, @Param("phone") String phone){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Courier> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("courier_name",courier_name);
        if(count == null){
            params.put("count",null);
        } else {
            params.put("count",Integer.parseInt(count));
        }
        if(state == null){
            params.put("state",null);
        } else {
            params.put("state",Integer.parseInt(state));
        }
        params.put("phone",phone);
        params.put("courier_id",courier_id);
        queryWrapper.allEq(params,false);
        IPage<Courier> iPage = courierService.page(new Page<>(page ,limit),queryWrapper);
        if(courierService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg( "获取送水员信息成功！");
            dataJsonUtils.setCount(courierService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取送水员信息失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加送水员
    * */
    @RequestMapping("/add")
    @ResponseBody
    public String addCourier(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String id = GenerateIdUtils.generateCourierID();
        while(courierService.getById(id) != null){
            id = GenerateIdUtils.generateCourierID();
        }
        String name = map.get("courier_name");
        String phone = map.get("phone");
        Courier courier = new Courier();

        QueryWrapper<Courier> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courier_name",name);
        if(courierService.getOne(queryWrapper) != null){
            dataJsonUtils.setMsg("送水员已经存在！");
            dataJsonUtils.setCode(0);
            return dataJsonUtils.toString();
        }

        courier.setCourierId(id);
        courier.setCourierName(name);
        courier.setPhone(phone);

        if(courierService.save(courier)){
            dataJsonUtils.setMsg("添加送水员信息成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加送水员信息失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
     * 删除送水员
     * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteCourierById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(courierService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除成功！");
        } else {
            dataJsonUtils.setMsg("删除失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
     *删除多个送水员
     * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteCourier(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!courierService.removeById(id)){
                error = true;
            }
        }
        if(error){
            dataJsonUtils.setMsg("顾客不存在！");
            dataJsonUtils.setCode(0);
        } else {
            dataJsonUtils.setMsg("删除顾客成功！");
            dataJsonUtils.setCode(200);
        }
        return dataJsonUtils.toString();
    }

    /*
     * 获取对应id饮水机信息
     * */
    @RequestMapping("/getById")
    @ResponseBody
    public String selectCourierById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Courier courier = courierService.getById(id);
        if(courier == null){
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("no such dispenser");
        } else {
            dataJsonUtils.setMsg("successfully");
            dataJsonUtils.setCode(200);
            dataJsonUtils.setCount(1);
            dataJsonUtils.setData(courier);
        }
        return dataJsonUtils.toString();
    }

    /*
     * 修改送水员信息
     * */
    @GetMapping("/update/{id}")
    public String courierUpdate(@PathVariable(value = "id") String id, Model model){
        Courier courier = courierService.getById(id);
        model.addAttribute("courier",courier);
        return "/admin/courierUpdate";
    }

    @RequestMapping("/updateCourier")
    @ResponseBody
    public String updateCourier(@RequestBody Map<String,String>map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Courier courier = new Courier();
        courier.setCourierId(map.get("courier_id"));
        courier.setPhone(map.get("phone"));
        courier.setCourierName(map.get("courier_name"));
        int state;
        if(map.get("state").equals("1"))
            state = 1;
        else
            state = 0;
        courier.setState(state);
        if(courierService.updateById(courier)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("修改送水员信息成功！");
        } else {
            dataJsonUtils.setMsg("修改送水员信息失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }
}


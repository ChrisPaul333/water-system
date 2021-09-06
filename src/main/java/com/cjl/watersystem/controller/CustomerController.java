package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.entity.Dispenser;
import com.cjl.watersystem.service.CustomerService;
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
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /*
    * 获取顾客信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
    public String getCustomerList(@Param("page") int page, @Param("limit") int limit, @Param("customer_id") String customer_id,
                                  @Param("customer_name") String customer_name, @Param("phone") String phone, @Param("address") String address,
                                  @Param("quantity") String quantity){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id",customer_id);
        params.put("customer_name",customer_name);
        params.put("phone",phone);
        params.put("address",address);
        if(quantity == null){
            params.put("quantity",null);
        } else {
            params.put("quantity",Integer.parseInt(quantity));
        }
        queryWrapper.allEq(params,false);
        IPage<Customer> iPage = customerService.page(new Page<>(page, limit),queryWrapper);
        if(customerService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("获取成功！");
            dataJsonUtils.setCount(customerService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
     * 添加顾客
     * */
    @RequestMapping("/add")
    @ResponseBody
    public String addCustomer(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        String customer_name = map.get("customer_name");
        String phone = map.get("phone");
        String address = map.get("address");

        queryWrapper.eq("customer_name",customer_name);
        if(customerService.getOne(queryWrapper) != null){
            dataJsonUtils.setMsg("顾客已经存在！");
            dataJsonUtils.setCode(0);
            return dataJsonUtils.toString();
        }

        String id = GenerateIdUtils.generateCustID();
        while(customerService.getById(id) != null){
            id = GenerateIdUtils.generateCustID();
        }

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCustomerName(customer_name);
        customer.setQuantity(0);

        if(customerService.save(customer)){
            dataJsonUtils.setMsg("添加顾客成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加顾客失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
     *删除顾客
     * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteCustomerById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(customerService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除成功");
        } else {
            dataJsonUtils.setMsg("顾客不存在！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    *删除多个顾客
    * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteCustomer(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!customerService.removeById(id)){
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
    * 修改顾客信息
    * */
    @GetMapping("/update/{id}")
    public String customerUpdate(@PathVariable(value = "id") String id, Model model){
        Customer customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "/admin/customerUpdate";
    }

    @RequestMapping("/updateCustomer")
    @ResponseBody
    public String updateCustomer(@RequestBody Map<String,String>map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Customer customer = new Customer();
        customer.setCustomerId(map.get("customer_id"));
        customer.setCustomerName(map.get("customer_name"));
        customer.setAddress(map.get("address"));
        customer.setPhone(map.get("phone"));
        if(customerService.updateById(customer)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("修改顾客信息成功！");
        } else {
            dataJsonUtils.setMsg("修改顾客信息失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }
}


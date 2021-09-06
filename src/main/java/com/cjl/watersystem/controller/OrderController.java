package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.entity.Order;
import com.cjl.watersystem.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*
    * 获取所有订单信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
    public String getOrderList(@Param("page") int page, @Param("limit") int limit, @Param("order_id") String order_id,
                               @Param("customer_id") String customer_id, @Param("amount") String amount,
                               @Param("state") String state, @Param("date") String date, @Param("water_id") String water_id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("order_id",order_id);
        params.put("customer_id",customer_id);
        params.put("water_id",water_id);
        if(amount == null){
            params.put("amount",null);
        } else {
            params.put("amount",Float.parseFloat(amount));
        }
        if(state == null){
            params.put("state",null);
        } else {
            params.put("state",Integer.parseInt(state));
        }
        queryWrapper.allEq(params,false);
        IPage<Order> iPage = orderService.page(new Page<>(page, limit),queryWrapper);
        if(orderService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(orderService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data unsuccessfully");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加订单
    * */
    @RequestMapping("/add")
    @ResponseBody
    public String addOrder(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String id = GenerateIdUtils.generateOrderID();
        while(orderService.getById(id) != null){
            id = GenerateIdUtils.generateOrderID();
        }

        String customer_id = map.get("customer_id");
        String date = map.get("date");
        String water_id = map.get("water_id");
        float amount = Float.parseFloat(map.get("amount"));

        Order order = new Order();
        order.setOrderId(id);
        order.setAmount(amount);
        order.setCustomerId(customer_id);
        order.setDate(date);
        order.setState(0);
        order.setWaterId(water_id);

        if(orderService.save(order)){
            dataJsonUtils.setMsg("添加订单成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加订单失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除订单
    * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteOrderById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(orderService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除成功");
        } else {
            dataJsonUtils.setMsg("删除失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
     *删除多个订单
     * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteOrder(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!orderService.removeById(id)){
                error = true;
            }
        }
        if(error){
            dataJsonUtils.setMsg("删除订单失败!");
            dataJsonUtils.setCode(0);
        } else {
            dataJsonUtils.setMsg("删除订单成功！");
            dataJsonUtils.setCode(200);
        }
        return dataJsonUtils.toString();
    }

    /*
     * 修改订单状态
     * */
    @GetMapping("/update/{id}")
    public String orderUpdate(@PathVariable(value = "id") String id, Model model){
        Order order = orderService.getById(id);
        model.addAttribute("order",order);
        return "/admin/orderUpdate";
    }

    @RequestMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(@RequestBody Map<String,String>map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int s;
        if(map.get("state").equals("1")) {
            s = 1;
        }
        else {
            s = 0;
        }
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("state",map.get("order_id")).set("state",s);
        if(orderService.update(updateWrapper)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("修改订单信息成功！");
        } else {
            dataJsonUtils.setMsg("修改订单信息失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }
}


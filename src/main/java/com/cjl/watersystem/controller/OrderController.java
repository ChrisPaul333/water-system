package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Order;
import com.cjl.watersystem.service.OrderService;
import com.cjl.watersystem.util.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*
    * 获取所有订单信息
    * */
    @RequestMapping("/getList")
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
}


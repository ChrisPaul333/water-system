package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.service.CustomerService;
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
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /*
    * 获取顾客信息
    * */
    @RequestMapping("/getList")
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
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(customerService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data unsuccessfully");
        }
        return dataJsonUtils.toString();
    }
}


package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.entity.Ticket;
import com.cjl.watersystem.service.CustomerService;
import com.cjl.watersystem.service.TicketService;
import com.cjl.watersystem.util.DataJsonUtils;
import com.cjl.watersystem.util.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CustomerService customerService;
    /*
    * 获取所有水票信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
    public String getTicketList(@Param("page") int page, @Param("limit") int limit, @Param("ticket_id") String ticket_id, @Param("customer_id")  String customer_id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Ticket> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("ticket_id",ticket_id);
        params.put("customer_id",customer_id);
        queryWrapper.allEq(params,false);
        IPage<Ticket> iPage = ticketService.page(new Page<>(page,limit),queryWrapper);
        if(ticketService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("获取成功！");
            dataJsonUtils.setCount(ticketService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加水票
    * */
    @RequestMapping("/add")
    @ResponseBody
    public String addTicket(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String customer_id = map.get("customer_id");
        if(customerService.getById(customer_id) == null){
            dataJsonUtils.setMsg("顾客不存在！");
            dataJsonUtils.setCode(0);
            return dataJsonUtils.toString();
        }
        int count = Integer.parseInt(map.get("count"));
        boolean error = false;
        for (int i = 1; i <= count; i++) {
            String id = GenerateIdUtils.generateTicketID();
            while (ticketService.getById(id) != null) {
                id = GenerateIdUtils.generateTicketID();
            }

            Ticket ticket = new Ticket();
            ticket.setCustomerId(customer_id);
            ticket.setTicketId(id);

            if(ticketService.save(ticket)){
                Customer customer = customerService.getById(customer_id);
                int quantity = customer.getQuantity() + 1;
                customer.setQuantity(quantity);
                customerService.updateById(customer);
            } else {
                error = true;
            }
        }
        if(!error){

            dataJsonUtils.setMsg("添加水票成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加水票失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除水票
    * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteTicketById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(ticketService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除成功");
        } else {
            dataJsonUtils.setMsg("水票不存在！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除多张水票
    * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteTicket(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!ticketService.removeById(id)){
                error = true;
            }
        }
        if(error){
            dataJsonUtils.setMsg("删除水票失败！");
            dataJsonUtils.setCode(0);
        } else {
            dataJsonUtils.setMsg("删除水票成功！");
            dataJsonUtils.setCode(200);
        }
        return dataJsonUtils.toString();
    }
}


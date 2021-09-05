package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Ticket;
import com.cjl.watersystem.service.TicketService;
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
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;
    /*
    * 获取所有水票信息
    * */
    @RequestMapping("/getList")
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
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(ticketService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data unsuccessfully");
        }
        return dataJsonUtils.toString();
    }
}


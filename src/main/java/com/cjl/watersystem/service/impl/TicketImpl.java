package com.cjl.watersystem.service.impl;

import com.cjl.watersystem.entity.Ticket;
import com.cjl.watersystem.mapper.TicketMapper;
import com.cjl.watersystem.service.TicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Service
public class TicketImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {
    @Autowired
    TicketMapper ticketMapper;

    @Override
    public List<Ticket> getTicketList(int pageNo, int pageSize, Map<String,String> params){
        int begin=(pageNo-1)*pageSize;
        return ticketMapper.getTicketList(begin,pageSize,params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return ticketMapper.getCount(params);
    }
}

package com.cjl.watersystem.service;

import com.cjl.watersystem.entity.Ticket;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
public interface TicketService extends IService<Ticket> {
    public List<Ticket> getTicketList(int pageNo, int pageSize, Map<String,String> params);

    public int getCount(Map<String,String> params);
}

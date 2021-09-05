package com.cjl.watersystem.mapper;

import com.cjl.watersystem.entity.Ticket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {
    public List<Ticket> getTicketList(int begin, int pageSize, @Param("params") Map<String,String> params);

    public int getCount(@Param("params") Map<String,String> params);
}

package com.cjl.watersystem.service.impl;

import com.cjl.watersystem.entity.Order;
import com.cjl.watersystem.mapper.OrderMapper;
import com.cjl.watersystem.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}

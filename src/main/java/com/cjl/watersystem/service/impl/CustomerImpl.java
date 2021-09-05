package com.cjl.watersystem.service.impl;

import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.mapper.CustomerMapper;
import com.cjl.watersystem.service.CustomerService;
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
public class CustomerImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}

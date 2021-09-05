package com.cjl.watersystem.mapper;

import com.cjl.watersystem.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}

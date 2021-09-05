package com.cjl.watersystem.mapper;

import com.cjl.watersystem.entity.Dispenser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Repository
@Mapper
public interface DispenserMapper extends BaseMapper<Dispenser> {

}

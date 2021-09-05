package com.cjl.watersystem.service.impl;

import com.cjl.watersystem.entity.Water;
import com.cjl.watersystem.mapper.WaterMapper;
import com.cjl.watersystem.service.WaterService;
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
public class WaterImpl extends ServiceImpl<WaterMapper, Water> implements WaterService {

}

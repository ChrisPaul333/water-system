package com.cjl.watersystem.service.impl;

import com.cjl.watersystem.entity.Dispenser;
import com.cjl.watersystem.mapper.DispenserMapper;
import com.cjl.watersystem.service.DispenserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjl
 * @since 2021-09-02
 */
@Service
public class DispenserImpl extends ServiceImpl<DispenserMapper, Dispenser> implements DispenserService {
//    @Autowired
//    private DispenserMapper dispenserMapper;
//
//    public List<Dispenser> getDispenserList(){
//        return dispenserMapper.selectList(null);
//    }
//
//    public Boolean addDispenser(Dispenser dispenser){
//
//    }
//
//    public String deleteDispenser(String id){
//
//    }
//
//    public String updateDispenser(Dispenser dispenser){
//
//    }
}

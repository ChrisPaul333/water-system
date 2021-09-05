package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Dispenser;
import com.cjl.watersystem.service.DispenserService;
import com.cjl.watersystem.util.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/dispenser")
public class DispenserController {
    @Autowired
    private DispenserService dispenserService;

    /*
    * 获取所有饮水机信息
    * */
    @RequestMapping("/getList")
    public String getDispenserList(@Param("page") int page, @Param("limit") int limit, @Param("dispenser_id") String dispenser_id,
                                   @Param("manufacturing_date") String manufacturing_date, @Param("state") String state){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        QueryWrapper<Dispenser> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("dispenser_id",dispenser_id);
        params.put("manufacturing_date",manufacturing_date);
        if(state == null){
            params.put("state",null);
        } else {
            params.put("state",Integer.parseInt(state));
        }
        queryWrapper.allEq(params,false);
        IPage<Dispenser> iPage = dispenserService.page(new Page<>(page, limit),queryWrapper);
        if(dispenserService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(dispenserService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data unsuccessfully");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加饮水机
    * */
    @RequestMapping("/add")
    public String addDispenser(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String id = map.get("dispenser_id");
        String manufacturing = map.get("manufacturing_date");
        String state = map.get("state");
        Dispenser dispenser = new Dispenser();
        if(dispenserService.getById(id) != null){
            dataJsonUtils.setMsg("dispenser has existed");
            dataJsonUtils.setCode(0);
            return dataJsonUtils.toString();
        }
        if(state.equals("0")){
            dispenser.setState(0);
        } else{
            dispenser.setState(1);
        }
        dispenser.setDispenserId(id);
        dispenser.setManufacturingDate(manufacturing);

        if(dispenserService.save(dispenser)){
            dataJsonUtils.setMsg("insert successfully");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("insert unsuccessfully");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除饮水机
    * */
    @RequestMapping("/deleteById")
    public String deleteDispenserById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(dispenserService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("delete successfully");
        } else {
            dataJsonUtils.setMsg("dispenser not exists");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取对应id饮水机信息
    * */
    @RequestMapping("/getById")
    public String selectDispenserById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Dispenser dispenser = dispenserService.getById(id);
        if(dispenser == null){
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("no such dispenser");
        } else {
            dataJsonUtils.setMsg("successfully");
            dataJsonUtils.setCode(200);
            dataJsonUtils.setCount(1);
            dataJsonUtils.setData(dispenser);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 修改饮水机状态
    * */
    @RequestMapping("/updateState")
    public String updateDispenserState(@RequestBody String id, String state){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int s;
        if(state.equals("1"))
            s = 1;
        else
            s = 0;
        UpdateWrapper<Dispenser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("dispenser_Id",id).set("state",s);
        if(dispenserService.update(updateWrapper)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update successfully");
        } else {
            dataJsonUtils.setMsg("update unsuccessfully");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取饮水机库存
    * */
    @RequestMapping("/getCount")
    public String getDispenserCount(){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int count = dispenserService.count();
        if(count >= 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("successfully");
            dataJsonUtils.setData(count);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("unsuccessfully");
        }
        return dataJsonUtils.toString();
    }
}


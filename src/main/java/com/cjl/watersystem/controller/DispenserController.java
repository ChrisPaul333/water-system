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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

@Controller
@RequestMapping("/dispenser")
public class DispenserController {
    @Autowired
    private DispenserService dispenserService;

    @GetMapping("/update/{id}")
    public String dispenserUpdate(@PathVariable(value = "id") String id,Model model){
        Dispenser dispenser = dispenserService.getById(id);
        model.addAttribute("dispenser",dispenser);
        return "/admin/dispenserUpdate";
    }

    /*
    * 获取所有饮水机信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
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
            dataJsonUtils.setMsg("获取饮水机信息成功！");
            dataJsonUtils.setCount(dispenserService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取饮水机信息失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加饮水机
    * */
    @RequestMapping("/add")
    @ResponseBody
    public String addDispenser(@RequestBody Map<String, String> map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String id = map.get("dispenser_id");
        String manufacturing = map.get("manufacturing_date");
        String state = map.get("state");
        Dispenser dispenser = new Dispenser();
        if(dispenserService.getById(id) != null){
            dataJsonUtils.setMsg("饮水机已存在！");
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
            dataJsonUtils.setMsg("添加饮水机成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加饮水机失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取对应id饮水机信息
    * */
    @RequestMapping("/getById")
    @ResponseBody
    public String selectDispenserById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Dispenser dispenser = dispenserService.getById(id);
        if(dispenser == null){
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("不存在该饮水机！");
        } else {
            dataJsonUtils.setMsg("获取饮水机信息成功！");
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
    @ResponseBody
    public String updateDispenserState(@RequestBody Map<String,String>map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int s;
        if(map.get("state").equals("1")){
            s=1;
            System.out.println(12312);
        }
        else {
            s = 0;
            System.out.println(2323);
        }
        UpdateWrapper<Dispenser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("dispenser_id",map.get("id")).set("state",s);
        if(dispenserService.update(updateWrapper)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("修改状态成功！");
        } else {
            dataJsonUtils.setMsg("修改状态失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取饮水机库存
    * */
    @RequestMapping("/getCount")
    @ResponseBody
    public String getDispenserCount(){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int count = dispenserService.count();
        if(count >= 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("获取库存信息成功！");
            dataJsonUtils.setData(count);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取库存信息失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 多条删除
    * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteDispenser(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!dispenserService.removeById(id)){
                error = true;
            }
        }
        if(error){
            dataJsonUtils.setMsg("删除饮水机失败！");
            dataJsonUtils.setCode(0);
        } else {
            dataJsonUtils.setMsg("删除饮水机成功！");
            dataJsonUtils.setCode(200);
        }
        return dataJsonUtils.toString();
    }

    /*
     * 删除饮水机
     * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteDispenserById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(dispenserService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除饮水机成功！");
        } else {
            dataJsonUtils.setMsg("删除饮水机失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }
}


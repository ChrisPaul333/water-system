package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Dispenser;
import com.cjl.watersystem.entity.Water;
import com.cjl.watersystem.service.WaterService;
import com.cjl.watersystem.util.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
@RequestMapping("/water")
public class WaterController {
    @Autowired
    private WaterService waterService;

    /*
    * 获取所有桶装水信息
    * */
    @RequestMapping("/getList")
    @ResponseBody
    public String getWaterList(@Param("page") int page, @Param("limit") int limit, @Param("water_id") String water_id, @Param("manufacturing_date") String manufacturing_date,
                               @Param("shelf_life") String shelf_life, @Param("volume") String volume, @Param("price") String price){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
//        List<Water> waterList = waterService.getBaseMapper().selectList(null);
        QueryWrapper<Water> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("water_id",water_id);
        params.put("manufacturing_date",manufacturing_date);
        if(shelf_life == null){
            params.put("shelf_life",null);
        } else {
            params.put("shelf_life",Integer.parseInt(shelf_life));
        }

        if(volume == null){
            params.put("volume",null);
        } else {
            params.put("volume",Integer.parseInt(volume));
        }

        if(volume == null){
            params.put("price",null);
        } else {
            params.put("price",new BigDecimal(price));
        }
        queryWrapper.allEq(params,false);
        IPage<Water> iPage = waterService.page(new Page<>(page,limit),queryWrapper);
        if(waterService.count(queryWrapper) > 0){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("获取桶装水信息成功！");
            dataJsonUtils.setCount(waterService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("获取桶装水信息失败！");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加桶装水信息
    * */
    @RequestMapping("/add")
    @ResponseBody
    public String addWater(@RequestBody Map<String, String> map){
        String water_id = map.get("water_id");
        String manufacturing_date = map.get("manufacturing_date");
        int shelf_life = Integer.parseInt(map.get("shelf_life"));
        int volume = Integer.parseInt(map.get("volume"));
        BigDecimal price = new BigDecimal(map.get("price"));

        Water water = new Water();
        water.setWaterId(water_id);
        water.setManufacturingDate(manufacturing_date);
        water.setShelfLife(shelf_life);
        water.setVolume(volume);
        water.setPrice(price);

        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(waterService.save(water)){
            dataJsonUtils.setMsg("添加桶装水成功！");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("添加桶装水失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取指定桶装水信息
    * */
    @RequestMapping("/getById")
    @ResponseBody
    public String getWaterById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Water water = waterService.getById(id);
        if(water == null){
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg(("不存在该桶装水！"));
        } else {
            dataJsonUtils.setMsg("获取桶装水信息成功！");
            dataJsonUtils.setCode(200);
            dataJsonUtils.setCount(1);
            dataJsonUtils.setData(water);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除指定桶装水
    * */
    @RequestMapping("/deleteById")
    @ResponseBody
    public String deleteWaterById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(waterService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("删除成功！");
        } else {
            dataJsonUtils.setMsg("删除失败！");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 删除多个桶装水信息
    * */
    @RequestMapping("/deleteManyById")
    @ResponseBody
    public String deleteWater(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for(String id : idList){
            if(!waterService.removeById(id)){
                error = true;
            }
        }
        if(error){
            dataJsonUtils.setMsg("删除失败！");
            dataJsonUtils.setCode(0);
        } else {
            dataJsonUtils.setMsg("删除成功！");
            dataJsonUtils.setCode(200);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取桶装水库存
    * */
    @RequestMapping("/getCount")
    @ResponseBody
    public String getWaterCount(){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int count = waterService.count();
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
    * 修改桶装水信息
    * */
    @GetMapping("/update/{id}")
    public String customerUpdate(@PathVariable(value = "id") String id, Model model){
        Water water = waterService.getById(id);
        model.addAttribute("water",water);
        return "/admin/waterUpdate";
    }

    /*
     * 修改饮水机价格
     * */
    @RequestMapping("/updateWater")
    @ResponseBody
    public String updateWaterPrice(@RequestBody Map<String,String>map){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        BigDecimal price = new BigDecimal(map.get("price"));
        Water water = new Water();
        water.setPrice(price);
        water.setWaterId(map.get("water_id"));
        if(waterService.updateById(water)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("修改桶装水价格成功！");
        } else {
            dataJsonUtils.setMsg("修改桶装水价格失败!");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }
}


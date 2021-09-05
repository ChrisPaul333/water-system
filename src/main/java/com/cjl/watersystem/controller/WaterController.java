package com.cjl.watersystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjl.watersystem.entity.Water;
import com.cjl.watersystem.service.WaterService;
import com.cjl.watersystem.util.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/water")
public class WaterController {
    @Autowired
    private WaterService waterService;

    /*
    * 获取所有桶装水信息
    * */
    @RequestMapping("/getList")
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
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(waterService.count(queryWrapper));
            dataJsonUtils.setData(iPage.getRecords());
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data unsuccessfully");
        }
        return dataJsonUtils.toString();
    }

    /*
    * 添加桶装水信息
    * */
    @RequestMapping("/add")
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
            dataJsonUtils.setMsg("insert successfully");
            dataJsonUtils.setCode(200);
        } else {
            dataJsonUtils.setMsg("insert unsuccessfully");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取指定桶装水信息
    * */
    @RequestMapping("/getById")
    public String getWaterById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Water water = waterService.getById(id);
        if(water == null){
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg(("no such water"));
        } else {
            dataJsonUtils.setMsg("successfully");
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
    public String deleteWaterById(@RequestBody String id){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if(waterService.removeById(id)){
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("delete successfully");
        } else {
            dataJsonUtils.setMsg("delete unsuccessfully");
            dataJsonUtils.setCode(0);
        }
        return dataJsonUtils.toString();
    }

    /*
    * 获取桶装水库存
    * */
    @RequestMapping("/getCount")
    public String getWaterCount(){
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        int count = waterService.count();
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


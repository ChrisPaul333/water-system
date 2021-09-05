package com.cjl.watersystem;

import com.cjl.watersystem.controller.DispenserController;
import com.cjl.watersystem.controller.WaterController;
import com.cjl.watersystem.entity.Admin;
import com.cjl.watersystem.entity.Customer;
import com.cjl.watersystem.entity.Dispenser;
import com.cjl.watersystem.entity.Water;
import com.cjl.watersystem.mapper.DispenserMapper;
import com.cjl.watersystem.service.AdminService;
import com.cjl.watersystem.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class WaterSystemApplicationTests {

    @Autowired
    private WaterController waterController;
    @Test
    public void test(){
        Water water = new Water();
        water.setWaterId("123456");
        water.setManufacturingDate("2021-09-03");
        water.setShelfLife(365);
        water.setVolume(50);
        BigDecimal a = new BigDecimal("20.00");
        water.setPrice(a);
        HashMap<String, String> map = new HashMap<>(){
            {
                put("water_id","123456789");
                put("manufacturing_date","2021-09-03");
                put("shelf_life","365");
                put("volume","50");
                put("price","20.00");
            }
        };
        System.out.println(waterController.addWater(map));
    }
}

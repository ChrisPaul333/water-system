package com.cjl.watersystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value="com.cjl.watersystem.mapper")
@SpringBootApplication
public class WaterSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterSystemApplication.class, args);
    }

}

package com.vnmaker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.vnmaker.mapper")
public class VNMakerApplication {
    public static void main(String[] args) {
        SpringApplication.run(VNMakerApplication.class, args);
    }
}

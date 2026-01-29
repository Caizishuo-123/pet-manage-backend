package com.imis.petmanagebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("com.imis.petmanagebackend.mapper")
public class PetManageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetManageBackendApplication.class, args);
    }

}

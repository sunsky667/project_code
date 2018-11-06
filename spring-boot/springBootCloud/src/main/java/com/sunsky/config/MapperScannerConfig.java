package com.sunsky.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MapperScannerConfig {

    @Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.sunsky.dao");
        return mapperScannerConfigurer;
    }
}

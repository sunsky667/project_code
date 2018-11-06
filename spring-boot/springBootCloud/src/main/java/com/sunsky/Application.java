package com.sunsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Configuration
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.sunsky")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}

package com.tabwu.changgou.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @PROJECT_NAME: changgou
 * @USER: tabwu
 * @DATE: 2021/10/26 13:17
 * @DESCRIPTION:
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.tabwu.changgou.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}

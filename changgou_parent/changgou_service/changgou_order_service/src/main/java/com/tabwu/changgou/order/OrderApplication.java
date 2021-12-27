package com.tabwu.changgou.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @PROJECT_NAME: changgou
 * @USER: tabwu
 * @DATE: 2021/11/24 14:30
 * @DESCRIPTION:
 */
@SpringBootApplication
@MapperScan("com.tabwu.changgou.order.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}

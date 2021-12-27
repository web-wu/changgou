package com.tabwu.changgou.gateway_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @PROJECT_NAME: changgou
 * @USER: tabwu
 * @DATE: 2021/10/25 13:53
 * @DESCRIPTION:  系统 网关
 */

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

    /**
     * ip address rateLimite
     * @return
     */
    @Bean("ipKeyResolver")
    public KeyResolver useKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                String hostName = exchange.getRequest().getRemoteAddress().getAddress().getHostName();
                return Mono.just(hostName);
            }
        };
    }

}

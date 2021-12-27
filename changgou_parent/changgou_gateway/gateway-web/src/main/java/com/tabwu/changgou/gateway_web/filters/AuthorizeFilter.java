package com.tabwu.changgou.gateway_web.filters;

import com.tabwu.changgou.gateway_web.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @PROJECT_NAME: changgou
 * @USER: tabwu
 * @DATE: 2021/10/27 17:20
 * @DESCRIPTION:
 */

@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "Authorization";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        // 登录页放行
        if (path.startsWith("/api/user/login")) {
            return chain.filter(exchange);
        }

        // 从请求头中获取 token
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        // 从cookie中获取token
        if (StringUtils.isEmpty(token)) {
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (first != null) {
                token = first.getValue();
            }
        }

        // 从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }

        // 如果 token 为null 直接拦截
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return response.setComplete();
        }

        // 验证token有效性
        try {
            Claims claims = JwtUtil.parseJWT(token);
            request.mutate().header(AUTHORIZE_TOKEN,claims.toString());
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

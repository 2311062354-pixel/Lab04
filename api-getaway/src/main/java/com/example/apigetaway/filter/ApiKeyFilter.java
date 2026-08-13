package com.example.apigetaway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter implements GlobalFilter, Ordered {

    @Value("${partner.api-key}")
    private String validApiKey;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();

        // Không phải API dành cho partner
        if (!path.startsWith("/api/public/courses")) {
            return chain.filter(exchange);
        }

        // Lấy API Key từ Header
        String apiKey =
                request.getHeaders().getFirst("X-API-KEY");

        // Không có hoặc sai API Key
        if (apiKey == null || !apiKey.equals(validApiKey)) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        // API Key đúng
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
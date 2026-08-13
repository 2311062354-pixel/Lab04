package com.example.apigetaway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthHeaderFilter implements GlobalFilter, Ordered {

    // Các đường dẫn không cần Authorization
    private static final List<String> OPEN_PATHS = List.of(
            "/api/auth/login",
            "/api/public/courses"
    );

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();

        // Kiểm tra đường dẫn có thuộc nhóm public hay không
        boolean isOpen = OPEN_PATHS.stream()
                .anyMatch(path::startsWith);

        // GET /api/courses/** là public
        // Người dùng có thể xem khóa học mà không cần đăng nhập
        boolean isPublicCourseRead =
                path.startsWith("/api/courses")
                        && request.getMethod().name().equals("GET");

        // Nếu là API public -> cho request đi tiếp
        if (isOpen || isPublicCourseRead) {
            return chain.filter(exchange);
        }

        // Nếu API cần đăng nhập nhưng không có Authorization
        if (!request.getHeaders().containsHeader("Authorization")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        // Có Authorization -> cho request đi tiếp
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
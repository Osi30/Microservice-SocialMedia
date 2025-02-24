package com.nam.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayRequestHeaderFilter implements GlobalFilter, Ordered {
    // Global Filter: filter will be applied to all routes
    // Ordered: like sort order for filter
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // create mew http request with the current request but add custom header X-Gateway-Request
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-Gateway-Request", "true")
                .build();
        // forward request with custom request to next filter to the chain
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -1; // -1 mean it will be in the most first filter
    }
}

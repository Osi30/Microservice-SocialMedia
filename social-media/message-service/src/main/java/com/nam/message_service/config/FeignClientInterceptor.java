package com.nam.message_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String GATEWAY_HEADER = "X-Gateway-Request";
    private static final String JWT_HEADER = "Authorization";

    // Called before feign request
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Retrieve current Http request attributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // Request session
            HttpServletRequest request = attributes.getRequest();
            String gatewayHeader = request.getHeader(GATEWAY_HEADER);
            if (gatewayHeader != null) {
                // Add header to outgoing request
                requestTemplate.header(GATEWAY_HEADER, gatewayHeader);
            }
            String jwtHeader = request.getHeader(JWT_HEADER);
            if (jwtHeader != null) {
                requestTemplate.header(JWT_HEADER, jwtHeader);
            }
        }
    }
}

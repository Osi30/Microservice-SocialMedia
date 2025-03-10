package com.nam.product_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignClientInterceptor implements RequestInterceptor {
    private static final String GATEWAY_HEADER = "X-Gateway-Request";
    private static final String JWT_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String gateway = request.getHeader(GATEWAY_HEADER);
            if (gateway != null) {
                requestTemplate.header(GATEWAY_HEADER, gateway);
            }
            String jwt = request.getHeader(JWT_HEADER);
            if (jwt != null) {
                requestTemplate.header(JWT_HEADER, jwt);
            }
        }
    }
}

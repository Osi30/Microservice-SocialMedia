package com.nam.product_service.service;

import com.nam.product_service.config.FeignClientInterceptor;
import com.nam.product_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8001/api/users",
configuration = FeignClientInterceptor.class)
public interface UserService {
    @GetMapping("/profile")
    UserDTO getUserProfileByToken(@RequestHeader("Authorization") String token);
}

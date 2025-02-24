package com.nam.message_service.service;

import com.nam.message_service.config.FeignClientInterceptor;
import com.nam.message_service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8001/api/users"
, configuration = FeignClientInterceptor.class)
public interface UserService {
    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable("id") String id);

    @GetMapping("/profile")
    UserDTO getUserProfileByToken(@RequestHeader("Authorization") String token);
}

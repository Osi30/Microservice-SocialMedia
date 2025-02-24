package com.nam.user_service.controller;

import com.nam.user_service.dto.AuthDTO;
import com.nam.user_service.dto.TokenDTO;
import com.nam.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthDTO authDTO) {
        userService.registerUser(authDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthDTO authDTO) {
        TokenDTO tokenDTO = userService.loginUser(authDTO);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }
}

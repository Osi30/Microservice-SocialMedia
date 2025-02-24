package com.nam.user_service.controller;

import com.nam.gateway.annotation.GatewayOnly;
import com.nam.user_service.dto.UserDTO;
import com.nam.user_service.entity.User;
import com.nam.user_service.service.JwtService;
import com.nam.user_service.service.UserMapperService;
import com.nam.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@GatewayOnly()
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserMapperService userMapperService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String token
    ) {
        User user = jwtService.getUserFromToken(token);
        UserDTO updatedUser = userService.updateUser(user.getId(), userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfileByToken(@RequestHeader("Authorization") String token) {
        User user = jwtService.getUserFromToken(token);
        UserDTO userDTO = userMapperService.toUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}

package com.nam.user_service.service.impl;

import com.nam.user_service.dto.AuthDTO;
import com.nam.user_service.dto.UserDTO;
import com.nam.user_service.entity.User;
import com.nam.user_service.exception.UserException;
import com.nam.user_service.service.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperServiceImpl implements UserMapperService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public User updateToUser(User user, UserDTO userDTO) {
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()){
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().isEmpty()){
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()){
            user.setUsername(userDTO.getUsername());
        }
        return user;
    }

    @Override
    public User toUser(AuthDTO authDTO) throws UserException {
        User user = new User();
        user.setUsername(authDTO.getUsername());
        user.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        if (authDTO.getEmail() != null && !authDTO.getEmail().isEmpty()){
            user.setEmail(authDTO.getEmail());
        }
        if (authDTO.getPhone() != null && !authDTO.getPhone().isEmpty()){
            user.setPhone(authDTO.getPhone());
        }
        return user;
    }
}

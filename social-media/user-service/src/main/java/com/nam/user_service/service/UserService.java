package com.nam.user_service.service;

import com.nam.user_service.dto.AuthDTO;
import com.nam.user_service.dto.TokenDTO;
import com.nam.user_service.dto.UserDTO;
import com.nam.user_service.exception.UserException;

import java.util.List;

public interface UserService {
    void registerUser(AuthDTO authDTO) throws UserException;
    TokenDTO loginUser(AuthDTO authDTO) throws UserException;
    List<UserDTO> getAllUsers() throws UserException;
    UserDTO getUserById(String id) throws UserException;
    UserDTO getUserByUsername(String username) throws UserException;
    UserDTO updateUser(String userId, UserDTO userDTO) throws UserException;
    void deleteUser(String id) throws UserException;
}

package com.nam.user_service.service;

import com.nam.user_service.dto.AuthDTO;
import com.nam.user_service.dto.UserDTO;
import com.nam.user_service.entity.User;
import com.nam.user_service.exception.UserException;

public interface UserMapperService {
    UserDTO toUserDTO(User user);
    User updateToUser(User user, UserDTO userDTO);
    User toUser(AuthDTO authDTO) throws UserException;
}

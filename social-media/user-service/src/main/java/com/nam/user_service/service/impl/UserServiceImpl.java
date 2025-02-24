package com.nam.user_service.service.impl;

import com.nam.user_service.dto.AuthDTO;
import com.nam.user_service.dto.TokenDTO;
import com.nam.user_service.dto.UserDTO;
import com.nam.user_service.entity.User;
import com.nam.user_service.exception.UserException;
import com.nam.user_service.repository.UserRepository;
import com.nam.user_service.service.JwtService;
import com.nam.user_service.service.UserMapperService;
import com.nam.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;
    private final CustomerUserServiceImpl customerUserServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void registerUser(AuthDTO authDTO) throws UserException {
        User existedUser = userRepository.findByUsername(authDTO.getUsername());
        if (existedUser != null) {
            throw new UserException("Username already exists: " + authDTO.getUsername());
        }
        User user = userMapperService.toUser(authDTO);
        userRepository.save(user);
    }

    @Override
    public TokenDTO loginUser(AuthDTO authDTO) throws UserException {
        Authentication authentication = authenticate(authDTO.getUsername(), authDTO.getPassword());
        String token = jwtService.generateToken(authentication);
        return new TokenDTO(token);
    }

    @Override
    public List<UserDTO> getAllUsers() throws UserException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found");
        }
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(userMapperService.toUserDTO(user));
        }
        return userDTOs;
    }

    @Override
    public UserDTO getUserById(String id) throws UserException {
        User user = getUserByUserId(id);
        return userMapperService.toUserDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) throws UserException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException("User not found with username: " + username);
        }
        return userMapperService.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) throws UserException {
        User user = getUserByUserId(userId);
        User updatedUser = userRepository.save(userMapperService.updateToUser(user, userDTO));
        return userMapperService.toUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(String id) throws UserException {
        User user = getUserByUserId(id);
        userRepository.delete(user);
    }

    private User getUserByUserId(String userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with id: " + userId));
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserServiceImpl.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("User not found with username: " + username);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password does not match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

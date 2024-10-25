package com.example.dreamshops.service.user;

import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.model.User;
import com.example.dreamshops.request.CreateUserRequest;
import com.example.dreamshops.request.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
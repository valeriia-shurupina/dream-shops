package com.example.dreamshops.service.user;

import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.UserRepository;
import com.example.dreamshops.request.CreateUserRequest;
import com.example.dreamshops.request.UpdateUserRequest;
import com.example.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return  userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
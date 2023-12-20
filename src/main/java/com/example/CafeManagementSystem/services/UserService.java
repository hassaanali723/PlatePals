package com.example.CafeManagementSystem.services;

import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.DTO.UserDTO;
import com.example.CafeManagementSystem.entities.User;
import com.example.CafeManagementSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getContactNumber(),
                        user.getEmail(), user.getPassword(), user.getStatus(), user.getRole()))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getContactNumber(),
                        user.getEmail(), user.getPassword(), user.getStatus(), user.getRole()));
    }

    public SuccessResponse signUp(UserDTO userDTO) {
        // Check if the email already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isEmpty()) {
            // If the email doesn't exist, create a new user
            User user = new User();
            user.setName(userDTO.getName());
            user.setContactNumber(userDTO.getContactNumber());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setStatus("false");
            user.setRole("user");
            userRepository.save(user);
        } else {
            return new SuccessResponse("Email already exists");
        }
        return new SuccessResponse("User Created Successfully");
    }

}

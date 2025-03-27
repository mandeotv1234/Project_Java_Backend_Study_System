package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.request.UserCreateRequest;
import com.DuanJava.ProjectJavaFirst.dto.request.UserUpdateRequest;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.exception.AppException;
import com.DuanJava.ProjectJavaFirst.exception.ErrorCode;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Tạo User
    public User createUser(UserCreateRequest request) {
        User user = new User();
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    // Lấy danh sách User
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy thông tin User theo ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Cập nhật toàn bộ User (PUT)
    public User updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    // Cập nhật một phần thông tin User (PATCH)
    public User patchUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(request.getPassword());
        if (request.getRole() != null) user.setRole(request.getRole());
        return userRepository.save(user);
    }

    // Xóa User theo ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

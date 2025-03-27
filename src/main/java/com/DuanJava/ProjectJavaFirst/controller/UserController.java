package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.request.ApiResponse;
import com.DuanJava.ProjectJavaFirst.dto.request.UserCreateRequest;
import com.DuanJava.ProjectJavaFirst.dto.request.UserUpdateRequest;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users") // Định nghĩa prefix API
public class UserController {

    @Autowired
    private UserService userService;

    // API tạo User (POST)
    @PostMapping
    public ApiResponse createUser(@RequestBody @Valid UserCreateRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(request)); // ✅ Không cần dùng <>
        return apiResponse;
    }


    // API lấy danh sách User (GET)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // API lấy chi tiết một User theo ID (GET)
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // API cập nhật toàn bộ thông tin User (PUT)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    // API cập nhật một phần thông tin User (PATCH)
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.patchUser(id, request);
    }

    // API xóa User theo ID (DELETE)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully!";
    }
}

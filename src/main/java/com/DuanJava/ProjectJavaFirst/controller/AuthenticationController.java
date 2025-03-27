package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.request.ApiResponse;
import com.DuanJava.ProjectJavaFirst.dto.request.AuthenticationRequest;
import com.DuanJava.ProjectJavaFirst.dto.request.UserCreateRequest;
import com.DuanJava.ProjectJavaFirst.dto.response.AuthenticationResponse;
import com.DuanJava.ProjectJavaFirst.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.DuanJava.ProjectJavaFirst.exception.AppException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

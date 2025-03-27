package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.request.AuthenticationRequest;
import com.DuanJava.ProjectJavaFirst.dto.request.UserCreateRequest;
import com.DuanJava.ProjectJavaFirst.dto.response.AuthenticationResponse;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.exception.AppException;
import com.DuanJava.ProjectJavaFirst.exception.ErrorCode;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // ✅ Sử dụng JwtService thay vì tự tạo token

    public AuthenticationResponse register(UserCreateRequest request) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Tạo user mới
        var user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
              request.getEmail(),
                request.getRole()
        );
        userRepository.save(user); // ✅ Lưu vào database

        // Tạo token từ JwtService
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, true);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Tạo token từ JwtService
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, true);
    }
}

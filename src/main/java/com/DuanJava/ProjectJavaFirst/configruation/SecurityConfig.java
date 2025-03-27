package com.DuanJava.ProjectJavaFirst.configruation;

import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import com.DuanJava.ProjectJavaFirst.security.JwtAuthenticationFilter;
import com.DuanJava.ProjectJavaFirst.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // ✅ Định nghĩa UserDetailsService để tránh vòng lặp
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    // ✅ AuthenticationProvider sử dụng UserDetailsService từ @Bean
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()

                // 🔹 Các API công khai (không cần đăng nhập)
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/public/**").permitAll() // Các API công khai khác

                // 🔹 Quyền của Admin (Quản lý toàn bộ hệ thống)
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/courses/manage/**").hasRole("ADMIN") // Admin quản lý khóa học
                .requestMatchers("/api/users/**").hasRole("ADMIN") // Quản lý người dùng

                // 🔹 Quyền của Teacher (Giảng viên)
                .requestMatchers("/api/courses/create").hasRole("TEACHER") // Tạo khóa học
                .requestMatchers("/api/courses/edit/**").hasRole("TEACHER") // Chỉnh sửa khóa học
                .requestMatchers("/api/courses/delete/**").hasRole("TEACHER") // Xóa khóa học
                .requestMatchers("/api/materials/**").hasRole("TEACHER") // Tạo tài liệu học
                .requestMatchers("/api/lectures/**").hasRole("TEACHER") // Quản lý bài giảng
                .requestMatchers("/api/assignments/create").hasRole("TEACHER") // Tạo bài tập
                .requestMatchers("/api/questions/add").hasRole("TEACHER") // Tạo bài tập
                .requestMatchers("/api/submissions/assign/**").hasRole("TEACHER") //Chấm bài tập
                .requestMatchers("/api/quizzes/create").hasRole("TEACHER") // Tạo bài kiểm tra
                .requestMatchers("/api/courses/students/**").hasRole("TEACHER") // Xem danh sách học viên

                // 🔹 Quyền của Student (Học viên)
                .requestMatchers("/api/courses/enroll/**").hasRole("STUDENT") // Đăng ký khóa học
                .requestMatchers("/api/courses/view/**").hasAnyRole("STUDENT", "TEACHER") // Xem khóa học
                .requestMatchers("/api/lectures/view/**").hasRole("STUDENT") // Xem bài giảng
                .requestMatchers("/api/materials/view/**").hasRole("STUDENT") // Xem tài liệu
                .requestMatchers("/api/submissions/submit").hasRole("STUDENT") // Nộp bài tập
                .requestMatchers("/api/assignments/course/**").hasRole("STUDENT") // Nộp bài tập
                .requestMatchers("/api/quiz-submissions/**").hasRole("STUDENT") // Bắt đầu bài kiểm tra
                .requestMatchers("/api/reviews/**").hasRole("STUDENT") // Đánh giá khóa học

                // 🔹 Các API cần xác thực mới truy cập được
                .anyRequest().authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}

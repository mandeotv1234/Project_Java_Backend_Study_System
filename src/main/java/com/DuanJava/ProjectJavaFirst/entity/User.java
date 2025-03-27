package com.DuanJava.ProjectJavaFirst.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role {
        USER, ADMIN, STUDENT, TEACHER
    }

    // Constructor có tham số
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Thêm phương thức `getAuthorities()` để sửa lỗi
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name()); // Trả về quyền với format ROLE_ADMIN, ROLE_USER, etc.
    }

    @Override
    public String getUsername() {
        return email; // Dùng email làm username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Không áp dụng tính năng khóa tài khoản
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Không áp dụng tính năng khóa tài khoản
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Không áp dụng tính năng hết hạn mật khẩu
    }

    @Override
    public boolean isEnabled() {
        return true; // Mặc định là user luôn được kích hoạt
    }
}

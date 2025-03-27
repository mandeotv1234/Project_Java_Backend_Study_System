package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.entity.Enrollment;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // ✅ Học viên đăng ký khóa học
    @PostMapping("/{courseId}")
    public ResponseEntity<Enrollment> enrollUser(@PathVariable Long courseId,
                                                 @AuthenticationPrincipal User user) {
        Enrollment enrollment = enrollmentService.enrollUser(user, courseId);
        return ResponseEntity.ok(enrollment);
    }

    // ✅ Kiểm tra xem học viên đã đăng ký chưa
    @GetMapping("/{courseId}")
    public ResponseEntity<Boolean> checkEnrollment(@PathVariable Long courseId,
                                                   @AuthenticationPrincipal User user) {
        Optional<Enrollment> enrollment = enrollmentService.getEnrollment(user.getId(), courseId);
        return ResponseEntity.ok(enrollment.isPresent());
    }
}

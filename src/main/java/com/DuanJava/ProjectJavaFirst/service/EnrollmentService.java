package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.entity.Enrollment;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public Enrollment enrollUser(User user, Long courseId) {
        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(Course.builder().id(courseId).build()) // ✅ Đổi courseId thành course
                .enrolledAt(LocalDateTime.now())
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public Optional<Enrollment> getEnrollment(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }
}

package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.CourseDTO;
import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.entity.Lecture;
import com.DuanJava.ProjectJavaFirst.entity.Material;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ✅ Lấy danh sách tất cả khóa học
    @GetMapping("/view")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // ✅ Lấy danh sách khóa học của giáo viên hiện tại
    @GetMapping("/teacher")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@AuthenticationPrincipal User teacher) {
        List<Course> courses = courseService.getCoursesByTeacher(teacher.getId());
        return ResponseEntity.ok(courses);
    }

    // ✅ Lấy thông tin khóa học theo ID
    @GetMapping("/view/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO,
                                               @AuthenticationPrincipal User teacher) {
        Course savedCourse = courseService.createCourse(courseDTO, teacher);
        return ResponseEntity.ok(savedCourse);
    }



}

package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.entity.Lecture;
import com.DuanJava.ProjectJavaFirst.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    // ✅ Lấy danh sách bài giảng theo khóa học
    @GetMapping("/view/course/{courseId}")
    public ResponseEntity<List<Lecture>> getLecturesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(lectureService.getLecturesByCourseId(courseId));
    }

    // ✅ Giảng viên thêm bài giảng mới
    @PostMapping
    public ResponseEntity<Lecture> addLecture(@RequestBody Lecture lecture) {
        return ResponseEntity.ok(lectureService.addLecture(lecture));
    }
}

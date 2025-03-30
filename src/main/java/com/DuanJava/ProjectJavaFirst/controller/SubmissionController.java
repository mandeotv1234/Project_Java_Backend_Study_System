package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.SubmissionDTO;
import com.DuanJava.ProjectJavaFirst.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    // ✅ 1. Sinh viên nộp bài
    @PostMapping("/submit")
    public ResponseEntity<SubmissionDTO> submitAssignment(@RequestBody SubmissionDTO submissionDTO) {
        return ResponseEntity.ok(submissionService.submitAssignment(submissionDTO));
    }

    // ✅ 2. Lấy danh sách bài nộp của sinh viên
    @GetMapping("/student")
    public ResponseEntity<List<SubmissionDTO>> getStudentSubmissions() {
        return ResponseEntity.ok(submissionService.getStudentSubmissions());
    }

    // ✅ 3. Giảng viên lấy danh sách bài nộp của một bài tập
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsForAssignment(@PathVariable Long assignmentId) {
        String teacherEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(submissionService.getSubmissionsForAssignment(assignmentId, teacherEmail));
    }

    // ✅ 4. Giảng viên chấm điểm bài nộp
    @PostMapping("/teacher/{submissionId}/grade")
    public ResponseEntity<SubmissionDTO> gradeSubmission(@PathVariable Long submissionId, @RequestParam Double grade) {
        String teacherEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(submissionService.gradeSubmission(submissionId, grade, teacherEmail));
    }

    // ✅ 5. Lấy chi tiết bài nộp theo ID
    @GetMapping("/teacher/{submissionId}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionService.getSubmissionById(submissionId));
    }

}

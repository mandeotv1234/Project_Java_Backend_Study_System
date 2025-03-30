package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.AssignmentDTO;
import com.DuanJava.ProjectJavaFirst.entity.Assignment;
import com.DuanJava.ProjectJavaFirst.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    // ✅ Lấy danh sách bài tập của một khóa học (STUDENT & TEACHER)
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByCourse(@PathVariable Long courseId) {
        List<AssignmentDTO> assignments = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }

    // ✅ Giảng viên tạo bài tập
    @PostMapping("/create")
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO,
                                                          @AuthenticationPrincipal String teacherEmail) {
        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
        return ResponseEntity.ok(createdAssignment);
    }

    // ✅ Xem chi tiết bài tập
    @GetMapping("/course/detail/{assignmentId}")
    public ResponseEntity<AssignmentDTO> getAssignmentDetail(@PathVariable Long assignmentId) {
        AssignmentDTO assignment = assignmentService.getAssignmentById(assignmentId);
        return ResponseEntity.ok(assignment);
    }

    // ✅ Giảng viên chỉnh sửa bài tập
    @PutMapping("/update/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long assignmentId,
                                                          @RequestBody AssignmentDTO assignmentDTO,
                                                          @AuthenticationPrincipal String teacherEmail) {
        AssignmentDTO updatedAssignment = assignmentService.updateAssignment(assignmentId, assignmentDTO, teacherEmail);
        return ResponseEntity.ok(updatedAssignment);
    }

    // ✅ Giảng viên xóa bài tập
    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId,
                                                   @AuthenticationPrincipal String teacherEmail) {
        assignmentService.deleteAssignment(assignmentId, teacherEmail);
        return ResponseEntity.ok("Assignment deleted successfully.");
    }
}

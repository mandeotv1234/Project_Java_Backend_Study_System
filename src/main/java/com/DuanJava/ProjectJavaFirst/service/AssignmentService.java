package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.AssignmentDTO;
import com.DuanJava.ProjectJavaFirst.entity.Assignment;
import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.repository.AssignmentRepository;
import com.DuanJava.ProjectJavaFirst.repository.CourseRepository;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // ✅ Lấy danh sách bài tập của khóa học
    public List<AssignmentDTO> getAssignmentsByCourse(Long courseId) {
        List<Assignment> assignments = assignmentRepository.findByCourseId(courseId);
        return assignments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Tạo bài tập mới (Giảng viên)
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        // Lấy email của giảng viên từ Security Context
        String teacherEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Course course = courseRepository.findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        System.out.println("Teacher Email from Security Context: " + teacherEmail);
        System.out.println("Course Owner Email: " + course.getTeacher().getEmail());

        // Kiểm tra quyền sở hữu khóa học
        if (!course.getTeacher().getEmail().equals(teacherEmail)) {
            throw new RuntimeException("Unauthorized: You are not the owner of this course.");
        }

        Assignment assignment = Assignment.builder()
                .title(assignmentDTO.getTitle())
                .description(assignmentDTO.getDescription())
                .dueDate(assignmentDTO.getDueDate())
                .course(course)
                .build();

        return convertToDTO(assignmentRepository.save(assignment));
    }

    // ✅ Xem chi tiết bài tập
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
        return convertToDTO(assignment);
    }

    // ✅ Cập nhật bài tập
    public AssignmentDTO updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO, String teacherEmail) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        // Kiểm tra quyền sở hữu
        if (!assignment.getCourse().getTeacher().getEmail().equals(teacherEmail)) {
            throw new RuntimeException("Unauthorized: You are not the owner of this course.");
        }

        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setDueDate(assignmentDTO.getDueDate());

        return convertToDTO(assignmentRepository.save(assignment));
    }

    // ✅ Xóa bài tập
    public void deleteAssignment(Long assignmentId, String teacherEmail) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        // Kiểm tra quyền sở hữu
        if (!assignment.getCourse().getTeacher().getEmail().equals(teacherEmail)) {
            throw new RuntimeException("Unauthorized: You are not the owner of this course.");
        }

        assignmentRepository.delete(assignment);
    }

    // ✅ Chuyển đổi từ Entity sang DTO
    private AssignmentDTO convertToDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        dto.setCourseId(assignment.getCourse().getId());
        return dto;
    }
}

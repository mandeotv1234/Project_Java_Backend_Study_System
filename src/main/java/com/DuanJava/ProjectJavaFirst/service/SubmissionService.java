package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.SubmissionDTO;
import com.DuanJava.ProjectJavaFirst.entity.Assignment;
import com.DuanJava.ProjectJavaFirst.entity.Submission;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.repository.AssignmentRepository;
import com.DuanJava.ProjectJavaFirst.repository.SubmissionRepository;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    // 🟢 1. Sinh viên nộp bài
    @Transactional
    public SubmissionDTO submitAssignment(SubmissionDTO submissionDTO) {
        String studentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        Assignment assignment = assignmentRepository.findById(submissionDTO.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        Submission submission = Submission.builder()
                .fileUrl(submissionDTO.getFileUrl())
                .comments(submissionDTO.getComments())
                .submittedAt(LocalDateTime.now())
                .assignment(assignment)
                .student(student)
                .build();

        return convertToDTO(submissionRepository.save(submission));
    }

    // 🟢 2. Lấy danh sách bài nộp của sinh viên
    public List<SubmissionDTO> getStudentSubmissions() {
        String studentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return submissionRepository.findByStudent(student).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 🟢 3. Giảng viên lấy danh sách bài nộp của một bài tập
    public List<SubmissionDTO> getSubmissionsForAssignment(Long assignmentId, String teacherEmail) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        if (!assignment.getCourse().getTeacher().getEmail().equals(teacherEmail)) {
            throw new RuntimeException("Unauthorized: You are not the owner of this course.");
        }

        return submissionRepository.findByAssignment(assignment).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 🟢 4. Giảng viên chấm điểm bài nộp
    @Transactional
    public SubmissionDTO gradeSubmission(Long submissionId, Double grade, String teacherEmail) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        if (!submission.getAssignment().getCourse().getTeacher().getEmail().equals(teacherEmail)) {
            throw new RuntimeException("Unauthorized: You are not the owner of this course.");
        }

        submission.setGrade(grade);
        return convertToDTO(submissionRepository.save(submission));
    }

    private SubmissionDTO convertToDTO(Submission submission) {
        return SubmissionDTO.builder()
                .id(submission.getId())
                .fileUrl(submission.getFileUrl())
                .comments(submission.getComments())
                .grade(submission.getGrade())
                .submittedAt(submission.getSubmittedAt())
                .assignmentId(submission.getAssignment().getId())
                .studentId(submission.getStudent().getId())
                .build();
    }
}

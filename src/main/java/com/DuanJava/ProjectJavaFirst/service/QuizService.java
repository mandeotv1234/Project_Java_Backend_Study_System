package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.QuizDTO;
import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.entity.Quiz;
import com.DuanJava.ProjectJavaFirst.repository.CourseRepository;
import com.DuanJava.ProjectJavaFirst.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;

    // 🟢 1. Tạo bài kiểm tra mới
    @Transactional
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Course course = courseRepository.findById(quizDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        Quiz quiz = Quiz.builder()
                .title(quizDTO.getTitle())
                .description(quizDTO.getDescription())
                .maxScore(quizDTO.getMaxScore())
                .course(course)
                .build();

        return convertToDTO(quizRepository.save(quiz));
    }

    // 🟢 2. Lấy thông tin bài kiểm tra theo ID
    public QuizDTO getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        return convertToDTO(quiz);
    }

    private QuizDTO convertToDTO(Quiz quiz) {
        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .maxScore(quiz.getMaxScore())
                .courseId(quiz.getCourse().getId())
                .build();
    }
}

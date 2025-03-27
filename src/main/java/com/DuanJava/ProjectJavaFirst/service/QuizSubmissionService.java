package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.AnswerSubmissionDTO;
import com.DuanJava.ProjectJavaFirst.dto.QuizSubmissionDTO;
import com.DuanJava.ProjectJavaFirst.entity.*;
import com.DuanJava.ProjectJavaFirst.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final AnswerSubmissionRepository answerSubmissionRepository;

    @Transactional
    public QuizSubmissionDTO submitQuiz(Long quizId, Long studentId, List<AnswerSubmissionDTO> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        QuizSubmission quizSubmission = QuizSubmission.builder()
                .quiz(quiz)
                .student(student)
                .submittedAt(LocalDateTime.now())
                .build();

        quizSubmissionRepository.save(quizSubmission);

        double totalScore = 0;
        for (AnswerSubmissionDTO answerDTO : answers) {
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found"));

            AnswerSubmission answerSubmission = AnswerSubmission.builder()
                    .quizSubmission(quizSubmission)
                    .question(question)
                    .chosenAnswer(answerDTO.getChosenAnswer())
                    .build();

            answerSubmissionRepository.save(answerSubmission);

            // Chấm điểm: Nếu đáp án đúng, cộng điểm
            if (question.getCorrectAnswer().equals(answerDTO.getChosenAnswer())) {
                totalScore += quiz.getMaxScore() / (double) quiz.getQuestions().size();
            }
        }

        quizSubmission.setScore(totalScore);
        quizSubmissionRepository.save(quizSubmission);

        return new QuizSubmissionDTO(quizSubmission.getId(), quizId, studentId, totalScore, LocalDateTime.now());
    }
}

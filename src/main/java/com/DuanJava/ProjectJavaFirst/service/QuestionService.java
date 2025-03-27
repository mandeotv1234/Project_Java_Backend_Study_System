package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.QuestionDTO;
import com.DuanJava.ProjectJavaFirst.entity.Question;
import com.DuanJava.ProjectJavaFirst.entity.Quiz;
import com.DuanJava.ProjectJavaFirst.repository.QuestionRepository;
import com.DuanJava.ProjectJavaFirst.repository.QuizRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    // 🟢 1. Thêm câu hỏi vào bài kiểm tra
    @Transactional
    public QuestionDTO addQuestionToQuiz(QuestionDTO questionDTO) {
        Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        Question question = Question.builder()
                .content(questionDTO.getContent())
                .optionA(questionDTO.getOptionA())
                .optionB(questionDTO.getOptionB())
                .optionC(questionDTO.getOptionC())
                .optionD(questionDTO.getOptionD())
                .correctAnswer(questionDTO.getCorrectAnswer())
                .quiz(quiz)
                .build();

        return convertToDTO(questionRepository.save(question));
    }

    // 🟢 2. Lấy danh sách câu hỏi của một bài kiểm tra
    public List<QuestionDTO> getQuestionsByQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        return questionRepository.findByQuiz(quiz).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private QuestionDTO convertToDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctAnswer(question.getCorrectAnswer())
                .quizId(question.getQuiz().getId())
                .build();
    }
}

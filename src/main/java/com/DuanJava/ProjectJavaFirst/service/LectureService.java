package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.entity.Lecture;
import com.DuanJava.ProjectJavaFirst.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public Lecture addLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public List<Lecture> getLecturesByCourseId(Long courseId) {
        return lectureRepository.findByCourseId(courseId);
    }
}

package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.CourseDTO;
import com.DuanJava.ProjectJavaFirst.dto.LectureDTO;
import com.DuanJava.ProjectJavaFirst.entity.Course;
import com.DuanJava.ProjectJavaFirst.entity.Lecture;
import com.DuanJava.ProjectJavaFirst.entity.Material;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.repository.CourseRepository;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Course createCourse(CourseDTO courseDTO, User teacher) {
        Course course = Course.builder()
                .title(courseDTO.getTitle())
                .description(courseDTO.getDescription())
                .category(courseDTO.getCategory())
                .duration(courseDTO.getDuration())
                .price(courseDTO.getPrice())
                .createdAt(courseDTO.getCreatedAt())
                .teacher(teacher)
                .build();

        if (courseDTO.getLectures() != null) {
            List<Lecture> lectures = courseDTO.getLectures().stream()
                    .map(dto -> {
                        Lecture lecture = Lecture.builder()
                                .title(dto.getTitle())
                                .description(dto.getDescription())
                                .createdAt(dto.getCreatedAt())
                                .course(course)
                                .build();

                        if (dto.getMaterials() != null) {
                            List<Material> materials = dto.getMaterials().stream()
                                    .map(mat -> Material.builder()
                                            .title(mat.getTitle())
                                            .type(mat.getType())
                                            .url(mat.getUrl())
                                            .lecture(lecture)
                                            .build())
                                    .toList();
                            lecture.setMaterials(materials);
                        }
                        return lecture;
                    }).toList();
            course.setLectures(lectures);
        }

        return courseRepository.save(course);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // ✅ Lấy danh sách khóa học của giáo viên theo ID
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }
}

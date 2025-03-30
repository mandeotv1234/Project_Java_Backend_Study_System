package com.DuanJava.ProjectJavaFirst.repository;

import com.DuanJava.ProjectJavaFirst.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);
}

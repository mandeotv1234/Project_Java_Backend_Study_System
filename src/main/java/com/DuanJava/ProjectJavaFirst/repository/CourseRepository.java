package com.DuanJava.ProjectJavaFirst.repository;

import com.DuanJava.ProjectJavaFirst.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

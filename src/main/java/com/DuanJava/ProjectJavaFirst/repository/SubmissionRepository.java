package com.DuanJava.ProjectJavaFirst.repository;

import com.DuanJava.ProjectJavaFirst.entity.Assignment;
import com.DuanJava.ProjectJavaFirst.entity.Submission;
import com.DuanJava.ProjectJavaFirst.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudent(User student);
    List<Submission> findByAssignment(Assignment assignment);
}

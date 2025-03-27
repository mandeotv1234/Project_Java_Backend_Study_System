package com.DuanJava.ProjectJavaFirst.repository;
import java.util.Optional;
import com.DuanJava.ProjectJavaFirst.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);  // Fix method name

    public Optional<User> findByUsername(String username);

   public Optional<User> findByEmail(String email);
}// Tìm User theo email}

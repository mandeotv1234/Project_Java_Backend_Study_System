package com.DuanJava.ProjectJavaFirst.repository;

import com.DuanJava.ProjectJavaFirst.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c WHERE (c.user1Id = ?1 AND c.user2Id = ?2) OR (c.user1Id = ?2 AND c.user2Id = ?1)")
    Optional<ChatRoom> findByUserIds(Long userId1, Long userId2);
    List<ChatRoom> findByUser1IdOrUser2Id(Long userId1, Long userId2);
    Optional<ChatRoom> findByUser1IdAndUser2IdOrUser2IdAndUser1Id(Long user1Id, Long user2Id, Long user2IdRev, Long user1IdRev);

}

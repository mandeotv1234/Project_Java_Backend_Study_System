package com.DuanJava.ProjectJavaFirst.service;



import com.DuanJava.ProjectJavaFirst.entity.ChatRoom;
import com.DuanJava.ProjectJavaFirst.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public List<ChatRoom> getUserChatRooms(Long userId) {
        return chatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
    }

    public ChatRoom createChatRoom(Long user1Id, Long user2Id) {
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByUserIds(user1Id, user2Id);
        if (existingChatRoom.isPresent()) {
            return existingChatRoom.get();
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .user1Id(user1Id)
                .user2Id(user2Id)
                .build();
        return chatRoomRepository.save(chatRoom);
    }
}


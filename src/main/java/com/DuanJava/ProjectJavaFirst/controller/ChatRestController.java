package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.entity.ChatRoom;
import com.DuanJava.ProjectJavaFirst.entity.Message;
import com.DuanJava.ProjectJavaFirst.repository.ChatRoomRepository;
import com.DuanJava.ProjectJavaFirst.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @GetMapping("/{userId}/{adminId}")
    public List<Message> getChatHistory(@PathVariable Long userId, @PathVariable Long adminId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUser1IdAndUser2IdOrUser2IdAndUser1Id(userId, adminId, userId, adminId);

        if (chatRoom.isEmpty()) {
            return List.of(); // Không có phòng chat -> Trả về danh sách rỗng
        }

        return messageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoom.get().getId());
    }
}

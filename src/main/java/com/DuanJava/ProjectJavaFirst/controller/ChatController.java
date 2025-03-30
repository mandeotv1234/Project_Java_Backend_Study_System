package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.dto.MessageDTO;
import com.DuanJava.ProjectJavaFirst.entity.ChatRoom;
import com.DuanJava.ProjectJavaFirst.entity.Message;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.repository.ChatRoomRepository;
import com.DuanJava.ProjectJavaFirst.repository.MessageRepository;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/sendMessage") // Client gửi tin vào đây (/app/sendMessage)
    @SendTo("/topic/messages") // Gửi tin nhắn đến tất cả client đang lắng nghe
    public Message sendMessage(MessageDTO messageDTO) {
        messageDTO.setTimestamp(LocalDateTime.now());
        // Tìm người gửi (sender)
        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // 🔥 Fix lỗi: Tìm người nhận (receiver) trước khi dùng nó
        User receiver = userRepository.findById(messageDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        // Kiểm tra xem phòng chat giữa sender và receiver đã có chưa
        ChatRoom chatRoom = chatRoomRepository.findByUserIds(messageDTO.getSenderId(), messageDTO.getReceiverId())
                .orElseGet(() -> {
                    ChatRoom newRoom = new ChatRoom();
                    newRoom.setUser1Id(messageDTO.getSenderId());
                    newRoom.setUser2Id(messageDTO.getReceiverId());
                    return chatRoomRepository.save(newRoom);
                });

        // Lưu tin nhắn vào database
        Message message = Message.builder()
                .sender(sender)   // ✅ Đúng: Gán User sender thay vì senderId
                .receiver(receiver) // ✅ Đúng: Gán User receiver thay vì receiverId
                .chatRoom(chatRoom)  // 🔥 Gán ChatRoom vào Message
                .content(messageDTO.getContent())
                .timestamp(LocalDateTime.now())
                .build();


        messageRepository.save(message);
        return message;
    }
}

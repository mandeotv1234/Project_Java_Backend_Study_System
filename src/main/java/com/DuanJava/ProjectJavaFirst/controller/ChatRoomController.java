package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.entity.ChatRoom;
import com.DuanJava.ProjectJavaFirst.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @GetMapping("/user/{userId}")
    public List<ChatRoom> getUserChatRooms(@PathVariable Long userId) {
        return chatRoomService.getUserChatRooms(userId);
    }

    @PostMapping
    public ChatRoom createChatRoom(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return chatRoomService.createChatRoom(user1Id, user2Id);
    }
}

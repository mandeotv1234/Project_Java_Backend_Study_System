package com.DuanJava.ProjectJavaFirst.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;  // Nội dung tin nhắn

    private LocalDateTime timestamp; // Thời gian gửi tin

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;  // Người gửi

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;  // Người nhận
    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    @JsonIgnore
    private ChatRoom chatRoom; // Phòng chat giữa 2 user

}

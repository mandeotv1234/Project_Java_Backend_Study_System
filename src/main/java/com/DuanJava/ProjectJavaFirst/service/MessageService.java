package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.dto.MessageDTO;
import com.DuanJava.ProjectJavaFirst.entity.Message;
import com.DuanJava.ProjectJavaFirst.entity.User;
import com.DuanJava.ProjectJavaFirst.repository.MessageRepository;
import com.DuanJava.ProjectJavaFirst.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Gửi tin nhắn
    @Transactional
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        String senderEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User receiver = userRepository.findById(messageDTO.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Message message = Message.builder()
                .content(messageDTO.getContent())
                .timestamp(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();

        return convertToDTO(messageRepository.save(message));
    }

    // Lấy danh sách tin nhắn giữa 2 người
    public List<MessageDTO> getMessages(Long receiverId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        return messageRepository.findBySenderIdAndReceiverId(user.getId(), receiver.getId())
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO convertToDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .build();
    }
}

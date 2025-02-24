package com.nam.message_service.controller;

import com.nam.gateway.annotation.GatewayOnly;
import com.nam.message_service.entity.Message;
import com.nam.message_service.service.MessageService;
import com.nam.message_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@GatewayOnly
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(
            @RequestBody Message message,
            @RequestHeader("Authorization") String token
    ) {
        String senderId = userService.getUserProfileByToken(token).getId();
        Message sendMessage = messageService.sendMessage(message, senderId, message.getReceiverId());
        return ResponseEntity.ok(sendMessage);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(
            @RequestHeader("Authorization") String token,
            @PathVariable String receiverId
    ) {
        String senderId = userService.getUserProfileByToken(token).getId();
        List<Message> messages = messageService.getMessagesBetweenSenderAndReceiver(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }
}

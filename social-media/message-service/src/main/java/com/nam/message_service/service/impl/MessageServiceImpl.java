package com.nam.message_service.service.impl;

import com.nam.message_service.dto.UserDTO;
import com.nam.message_service.entity.Message;
import com.nam.message_service.exception.MessageException;
import com.nam.message_service.repository.MessageRepository;
import com.nam.message_service.service.MessageService;
import com.nam.message_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Override
    public Message sendMessage(Message message, String senderId, String receiverId) throws MessageException {
        UserDTO receiver = userService.getUserById(receiverId);
        message.setSenderId(senderId);
        message.setReceiverId(receiver.getId());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesBetweenSenderAndReceiver(String senderId, String receiverId) throws MessageException {
        return messageRepository.findMessagesBySenderIdAndReceiverIdOrderByCreatedAtDesc(senderId, receiverId);
    }
}

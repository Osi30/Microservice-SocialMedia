package com.nam.message_service.service;

import com.nam.message_service.entity.Message;
import com.nam.message_service.exception.MessageException;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message, String senderId, String receiverId) throws MessageException;
    List<Message> getMessagesBetweenSenderAndReceiver(String senderId, String receiverId) throws MessageException;
}

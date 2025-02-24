package com.nam.message_service.repository;

import com.nam.message_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesBySenderIdAndReceiverIdOrderByCreatedAtDesc(String senderId, String receiverId);
}

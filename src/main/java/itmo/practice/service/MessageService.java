package itmo.practice.service;

import itmo.practice.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import itmo.practice.domain.Chat;
import itmo.practice.repository.MessageRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> findAllByChat(Chat chat) {
        return messageRepository.findAllByChatOrderByIdDesc(chat);
    }
}

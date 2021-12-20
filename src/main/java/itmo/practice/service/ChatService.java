package itmo.practice.service;

import itmo.practice.domain.Chat;
import itmo.practice.domain.Message;
import itmo.practice.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import itmo.practice.repository.ChatRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public List<Chat> findAll() {
        return chatRepository.findAllByOrderByCreationTimeDesc();
    }

    @Transactional
    public Chat findById(Long id) {
        return id == null ? null : chatRepository.findById(id).orElse(null);
    }

    public void writeMessage(Client client, Chat chat, Message message) {
        chat.addMessage(client, message);
        chatRepository.save(chat);
    }
}

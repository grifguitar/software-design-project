package itmo.practice.repository;

import itmo.practice.domain.Chat;
import itmo.practice.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatOrderByIdDesc(Chat chat);
}

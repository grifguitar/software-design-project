package itmo.practice.repository;

import itmo.practice.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByOrderByCreationTimeDesc();
}

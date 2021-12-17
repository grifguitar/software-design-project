package itmo.practice.repository;

import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByIdDesc(Post post);
}
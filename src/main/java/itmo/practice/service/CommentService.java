package itmo.practice.service;

import itmo.practice.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import itmo.practice.domain.Post;
import itmo.practice.repository.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> findAllByPost(Post post) {return commentRepository.findAllByPostOrderByIdDesc(post); }
}
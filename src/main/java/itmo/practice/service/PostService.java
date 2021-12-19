package itmo.practice.service;

import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import itmo.practice.domain.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import itmo.practice.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    @Transactional
    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment(Client client, Post post, Comment comment) {
        post.addComment(client, comment);
        postRepository.save(post);
    }
}
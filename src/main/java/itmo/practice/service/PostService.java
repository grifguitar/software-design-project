package itmo.practice.service;

import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import itmo.practice.domain.Client;
import org.springframework.stereotype.Service;
import itmo.practice.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment(Client client, Post post, Comment comment) {
        post.addComment(client, comment);
        postRepository.save(post);
    }
}
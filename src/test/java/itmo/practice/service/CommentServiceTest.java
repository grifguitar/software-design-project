package itmo.practice.service;

import itmo.practice.domain.Client;
import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CommentServiceTest {
    private static final int ITERATIONS = 10;

    private final MockCommentRepository commentRepository = new MockCommentRepository();
    private final CommentService service = new CommentService(commentRepository);

    @Test
    public void findAllByPost() {
        Post post = new Post();
        post.setComments(new ArrayList<>());

        Assert.assertEquals(0, service.findAllByPost(post).size());

        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            Comment comment = new Comment();
            comment.setId(i);
            post.addComment(new Client(), comment);
            comments.add(0, comment);
        }

        Assert.assertEquals(comments, service.findAllByPost(post));
    }
}

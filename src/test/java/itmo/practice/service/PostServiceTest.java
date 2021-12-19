package itmo.practice.service;

import itmo.practice.domain.Client;
import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class PostServiceTest {
    private static final int ITERATIONS = 10;

    private final MockPostRepository postRepository = new MockPostRepository();
    private final PostService service = new PostService(postRepository);

    @Test
    public void findAllTest() {
        Assert.assertEquals(0, service.findAll().size());

        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            Post post = postRepository.save(new Post());
            posts.add(0, post);
        }

        Assert.assertEquals(posts, service.findAll());
    }

    @Test
    public void findByIdTest() {
        Assert.assertNull(service.findById(0L));

        Post post = postRepository.save(new Post());

        Assert.assertNotNull(service.findById(post.getId()));
    }

    @Test
    public void writeCommentTest() {
        Client client = new Client();
        Comment comment = new Comment();
        Post post = postRepository.save(new Post());

        service.writeComment(client, post, comment);

        Assert.assertTrue(post.getComments().contains(comment));
    }

    @After
    public void after() {
        postRepository.deleteAll();
    }
}

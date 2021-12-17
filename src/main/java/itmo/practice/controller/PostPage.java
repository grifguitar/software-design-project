package itmo.practice.controller;

import itmo.practice.domain.Comment;
import itmo.practice.domain.Post;
import itmo.practice.domain.Client;
import itmo.practice.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import itmo.practice.form.CommentCredentials;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static itmo.practice.form.CommentCredentials.encode;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("post/{id:\\d{1,10}}")
    public String postPageGet(@PathVariable long id, Model model, HttpSession httpSession) {
        if (postService.findById(id) == null) {
            putMessage(httpSession, "Chat does not exist!");
            return "redirect:/";
        }
        Client client = getClient(httpSession);
        for (Post post : client.getPosts()) {
            if (id == post.getId()) {
                model.addAttribute("commentForm", new CommentCredentials());
                model.addAttribute("isLogged", getClient(httpSession) != null);
                model.addAttribute("postGet", postService.findById(id));
                List<CommentCredentials> resComments = new ArrayList<>();
                for (Comment comment : postService.findById(id).getComments()) {
                    CommentCredentials decodeComment = new CommentCredentials();
                    decodeComment.setLogin(comment.getClient().getLogin());
                    decodeComment.setText(encode(comment.getText()));
                    resComments.add(decodeComment);
                }
                model.addAttribute("comments", resComments);
                return "PostPage";
            }
        }
        putMessage(httpSession, "Access is denied!");
        return "redirect:/";
    }

    @GetMapping("post/{id:\\d{10,}}")
    public String postPageGetMore(@PathVariable String id, Model model, HttpSession httpSession) {
        putMessage(httpSession, "Chat does not exist!");
        return "redirect:/";
    }

    @PostMapping("post/{id:\\d{1,10}}")
    public String postPagePost(@PathVariable long id, @Valid @ModelAttribute("commentForm") CommentCredentials commentCredentials,
                               BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            List<CommentCredentials> resComments = new ArrayList<>();
            for (Comment comment : postService.findById(id).getComments()) {
                CommentCredentials decodeComment = new CommentCredentials();
                decodeComment.setLogin(comment.getClient().getLogin());
                decodeComment.setText(encode(comment.getText()));
                resComments.add(decodeComment);
            }
            model.addAttribute("comments", resComments);
            model.addAttribute("postGet", postService.findById(id));
            model.addAttribute("isLogged", getClient(httpSession) != null);
            model.addAttribute("commentForm", commentCredentials);
            return "PostPage";
        }
        if (postService.findById(id) == null) {
            putMessage(httpSession, "Chat does not exist!");
            return "redirect:/";
        }
        Comment comment = new Comment();
        comment.setText(encode(commentCredentials.getText()));
        postService.writeComment(getClient(httpSession), postService.findById(id), comment);
        putMessage(httpSession, "You sent a message!");
        return "redirect:/post/" + id;
    }

    @PostMapping("post/{id:\\d{10,}}")
    public String postPagePostMore(@PathVariable String id, HttpSession httpSession) {
        putMessage(httpSession, "Chat does not exist!");
        return "redirect:/";
    }
}
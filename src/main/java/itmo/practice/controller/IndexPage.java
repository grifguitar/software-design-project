package itmo.practice.controller;

import itmo.practice.domain.Client;
import itmo.practice.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class IndexPage extends Page {
    private final PostService postService;

    public IndexPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        Client client;
        if ((client = getClient(httpSession)) != null) {
            model.addAttribute("posts", client.getPosts());
        } else {
            model.addAttribute("posts", new ArrayList<>());
        }
        return "IndexPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        unsetClient(httpSession);
        return "redirect:/";
    }
}
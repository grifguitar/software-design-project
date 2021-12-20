package itmo.practice.controller;

import itmo.practice.domain.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class IndexPage extends Page {
    @GetMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        Client client;
        if ((client = getClient(httpSession)) != null) {
            model.addAttribute("chats", client.getChats());
        } else {
            model.addAttribute("chats", new ArrayList<>());
        }
        return "IndexPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        unsetClient(httpSession);
        return "redirect:/";
    }
}

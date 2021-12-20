package itmo.practice.controller;

import itmo.practice.domain.Chat;
import itmo.practice.domain.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexPage extends Page {
    @GetMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        Client client;
        if ((client = getClient(httpSession)) != null) {
            List<Chat> chats = client.getChats();
            Map<Long, Chat> chatsMap = new HashMap<>();
            for (Chat c : chats) {
                chatsMap.put(c.getId(), c);
            }
            model.addAttribute("chats", chatsMap.values());
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

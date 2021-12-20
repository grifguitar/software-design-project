package itmo.practice.controller;

import itmo.practice.domain.Chat;
import itmo.practice.domain.Message;
import itmo.practice.domain.Client;
import itmo.practice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import itmo.practice.form.MessageCredentials;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static itmo.practice.form.MessageCredentials.encode;

@RequiredArgsConstructor
@Controller
public class ChatPage extends Page {
    private final static String REDIRECT = "redirect:/";
    private final static String CHAT = "chat/";
    private final ChatService chatService;

    @GetMapping(CHAT + "{id:\\d{1,10}}")
    public String chatPageGet(@PathVariable long id, Model model, HttpSession httpSession) {
        if (chatService.findById(id) == null) {
            putNotification(httpSession, "Chat does not exist!");
            return REDIRECT;
        }
        Client client = getClient(httpSession);
        for (Chat chat : client.getChats()) {
            if (id == chat.getId()) {
                model.addAttribute("messageForm", new MessageCredentials());
                model.addAttribute("isLogged", getClient(httpSession) != null);
                model.addAttribute("chatGet", chatService.findById(id));
                model.addAttribute("messages", findMessageById(id));
                return "ChatPage";
            }
        }
        putNotification(httpSession, "Access is denied!");
        return REDIRECT;
    }

    @GetMapping(CHAT + "{id:\\d{10,}}")
    public String chatPageGetMore(@PathVariable String id, HttpSession httpSession) {
        putNotification(httpSession, "Chat does not exist!");
        return REDIRECT;
    }

    @PostMapping(CHAT + "{id:\\d{1,10}}")
    public String chatPagePost(@PathVariable long id,
                               @Valid @ModelAttribute("messageForm") MessageCredentials messageCredentials,
                               BindingResult bindingResult,
                               HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messages", findMessageById(id));
            model.addAttribute("chatGet", chatService.findById(id));
            model.addAttribute("isLogged", getClient(httpSession) != null);
            model.addAttribute("messageForm", messageCredentials);
            return "ChatPage";
        }
        if (chatService.findById(id) == null) {
            putNotification(httpSession, "Chat does not exist!");
            return REDIRECT;
        }
        Message message = new Message();
        message.setText(encode(messageCredentials.getText()));
        chatService.writeMessage(getClient(httpSession), chatService.findById(id), message);
        putNotification(httpSession, "You sent a message!");
        return REDIRECT + CHAT + id;
    }

    @PostMapping(CHAT + "{id:\\d{10,}}")
    public String chatPagePostMore(@PathVariable String id, HttpSession httpSession) {
        putNotification(httpSession, "Chat does not exist!");
        return REDIRECT;
    }

    private List<MessageCredentials> findMessageById(long id) {
        List<MessageCredentials> messages = new ArrayList<>();
        for (Message message : chatService.findById(id).getMessages()) {
            MessageCredentials decodeMessage = new MessageCredentials();
            decodeMessage.setLogin(message.getClient().getLogin());
            decodeMessage.setText(encode(message.getText()));
            messages.add(decodeMessage);
        }
        return messages;
    }
}

package itmo.practice.controller;

import itmo.practice.domain.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import itmo.practice.domain.Client;
import itmo.practice.form.FriendCredentials;
import itmo.practice.form.validator.FriendValidator;
import itmo.practice.service.ClientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FriendsPage extends Page {
    private final ClientService clientService;
    private final FriendValidator friendValidator;

    @InitBinder("friend")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(friendValidator);
    }

    @GetMapping("/friends")
    public String friendsGet(Model model, HttpSession httpSession) {
        model.addAttribute("friends", getClient(httpSession).getFriends());
        model.addAttribute("friend", new FriendCredentials().setCurrentLogin(getClient(httpSession).getLogin()));
        return "FriendsPage";
    }

    @PostMapping("/friends")
    public String friendsPost(Model model, @Valid @ModelAttribute("friend") FriendCredentials friendCredentials,
                              BindingResult bindingResult,
                              HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("friends", getClient(httpSession).getFriends());
            return "FriendsPage";
        }

        putNotification(httpSession, "Chat between " + friendCredentials.getCurrentLogin() + " and "
                + friendCredentials.getLogin() + "!");

        Client currentClient = clientService.findByLogin(friendCredentials.getCurrentLogin());
        Client client = clientService.findByLogin(friendCredentials.getLogin());
        for (Chat currentClientChat : currentClient.getChats()) {
            for (Chat clientChat : client.getChats()) {
                if (currentClientChat.getId() == clientChat.getId()) {
                    return "redirect:/chat/" + currentClientChat.getId();
                }
            }
        }

        List<Client> clients = new ArrayList<>();
        clients.add(clientService.findByLogin(friendCredentials.getLogin()));
        clients.add(clientService.findByLogin(friendCredentials.getCurrentLogin()));

        Chat chat = new Chat();
        chat.setTitle("Chat between " + friendCredentials.getCurrentLogin() + " and " + friendCredentials.getLogin());

        clientService.createChat(clients, chat);
        putNotification(httpSession, "You created chat!");

        return "redirect:/chat/" + chat.getId();
    }
}

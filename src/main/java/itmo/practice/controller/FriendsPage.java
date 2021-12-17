package itmo.practice.controller;

import itmo.practice.domain.Post;

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

@Controller
public class FriendsPage extends Page {
    private final ClientService clientService;
    private final FriendValidator friendValidator;

    public FriendsPage(ClientService clientService, FriendValidator friendValidator) {
        this.clientService = clientService;
        this.friendValidator = friendValidator;
    }

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

        putMessage(httpSession, "Chat between " + friendCredentials.getCurrentLogin() + " and "
                + friendCredentials.getLogin() + "!");

        Client currentClient = clientService.findByLogin(friendCredentials.getCurrentLogin());
        Client client = clientService.findByLogin(friendCredentials.getLogin());
        for (Post currentClientPost : currentClient.getPosts()) {
            for (Post clientPost : client.getPosts()) {
                if (currentClientPost.getId() == clientPost.getId()) {
                    return "redirect:/post/" + currentClientPost.getId();
                }
            }
        }

        List<Client> clients = new ArrayList<>();
        clients.add(clientService.findByLogin(friendCredentials.getLogin()));
        clients.add(clientService.findByLogin(friendCredentials.getCurrentLogin()));

        Post post = new Post();
        post.setTitle("Chat between " + friendCredentials.getCurrentLogin() + " and " + friendCredentials.getLogin());

        clientService.writePost(clients, post);
        putMessage(httpSession, "You created chat!");

        return "redirect:/post/" + post.getId();
    }
}
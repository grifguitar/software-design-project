package itmo.practice.controller;

import itmo.practice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import itmo.practice.form.FriendCredentials;
import itmo.practice.form.validator.FriendCredentialsValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class WriteFriendPage extends Page {
    private final ClientService clientService;
    private final FriendCredentialsValidator friendCredentialsValidator;

    @InitBinder("friend")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(friendCredentialsValidator);
    }

    @GetMapping("/writeFriend")
    public String writeFriendGet(Model model, HttpSession httpSession) {
        if (getClient(httpSession) == null) {
            model.addAttribute("friend", new FriendCredentials());
            return "WriteFriendPage";
        }
        model.addAttribute("friend", new FriendCredentials().setCurrentLogin(getClient(httpSession).getLogin()));
        return "WriteFriendPage";
    }

    @PostMapping("/writeFriend")
    public String writeFriendPost(@Valid @ModelAttribute("friend") FriendCredentials friendCredentials,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WriteFriendPage";
        }

        clientService.writeFriend(getClient(httpSession), clientService.findByLogin(friendCredentials.getLogin()));
        putNotification(httpSession, "You added new friend!");

        return "redirect:/";
    }
}
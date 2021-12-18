package itmo.practice.controller;

import itmo.practice.form.ClientCredentials;
import itmo.practice.form.validator.ClientCredentialsEnterValidator;
import itmo.practice.security.Guest;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class EnterPage extends Page {
    private final ClientService clientService;
    private final ClientCredentialsEnterValidator clientCredentialsEnterValidator;

    @InitBinder("enterForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(clientCredentialsEnterValidator);
    }

    @Guest
    @GetMapping("/enter")
    public String enter(Model model) {
        model.addAttribute("enterForm", new ClientCredentials());
        return "EnterPage";
    }

    @Guest
    @PostMapping("/enter")
    public String enter(@Valid @ModelAttribute("enterForm") ClientCredentials enterForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "EnterPage";
        }

        setClient(httpSession, clientService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword()));
        putMessage(httpSession, "Hello, " + getClient(httpSession).getLogin() + "!");

        return "redirect:/";
    }
}
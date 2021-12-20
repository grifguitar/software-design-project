package itmo.practice.controller;

import itmo.practice.form.ClientCredentials;
import itmo.practice.form.validator.ClientCredentialsRegisterValidator;
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
public class RegisterPage extends Page {
    private final ClientService clientService;
    private final ClientCredentialsRegisterValidator clientCredentialsRegisterValidator;

    @InitBinder("registerForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(clientCredentialsRegisterValidator);
    }

    @Guest
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("registerForm", new ClientCredentials());
        return "RegisterPage";
    }

    @Guest
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("registerForm") ClientCredentials registerForm,
                           BindingResult bindingResult,
                           HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "RegisterPage";
        }

        setClient(httpSession, clientService.register(registerForm));
        putNotification(httpSession, "Congrats, you have been registered!");

        return "redirect:/";
    }
}
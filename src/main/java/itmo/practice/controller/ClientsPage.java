package itmo.practice.controller;

import itmo.practice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ClientsPage extends Page {
    private final ClientService clientService;

    @GetMapping("/clients/all")
    public String clients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "ClientsPage";
    }
}

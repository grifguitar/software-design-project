package itmo.practice.controller;

import itmo.practice.domain.Client;
import itmo.practice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

public class Page {
    private static final String USER_ID_SESSION_KEY = "clientId";
    private static final String MESSAGE_SESSION_KEY = "message";

    @Autowired
    private ClientService clientService;

    @ModelAttribute("client")
    public Client getClient(HttpSession httpSession) {
        return clientService.findById((Long) httpSession.getAttribute(USER_ID_SESSION_KEY));
    }

    @ModelAttribute("message")
    public String getMessage(HttpSession httpSession) {
        String message = (String) httpSession.getAttribute(MESSAGE_SESSION_KEY);
        httpSession.removeAttribute(MESSAGE_SESSION_KEY);
        return message;
    }

    void setClient(HttpSession httpSession, Client client) {
        if (client != null) {
            httpSession.setAttribute(USER_ID_SESSION_KEY, client.getId());
        } else {
            unsetClient(httpSession);
        }
    }

    void unsetClient(HttpSession httpSession) {
        httpSession.removeAttribute(USER_ID_SESSION_KEY);
    }

    public void putMessage(HttpSession httpSession, String message) {
        httpSession.setAttribute(MESSAGE_SESSION_KEY, message);
    }
}
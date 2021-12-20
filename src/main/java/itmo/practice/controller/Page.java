package itmo.practice.controller;

import itmo.practice.domain.Client;
import itmo.practice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

public class Page {
    private static final String USER_ID_SESSION_KEY = "clientId";
    private static final String NOTIFICATION_SESSION_KEY = "notification";

    @Autowired
    private ClientService clientService;

    @ModelAttribute("client")
    public Client getClient(HttpSession httpSession) {
        return clientService.findById((Long) httpSession.getAttribute(USER_ID_SESSION_KEY));
    }

    @ModelAttribute("notification")
    public String getNotification(HttpSession httpSession) {
        String notification = (String) httpSession.getAttribute(NOTIFICATION_SESSION_KEY);
        httpSession.removeAttribute(NOTIFICATION_SESSION_KEY);
        return notification;
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

    public void putNotification(HttpSession httpSession, String notification) {
        httpSession.setAttribute(NOTIFICATION_SESSION_KEY, notification);
    }
}
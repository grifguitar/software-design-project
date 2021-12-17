package itmo.practice.form.validator;

import itmo.practice.service.ClientService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import itmo.practice.form.FriendCredentials;

@Component
public class FriendCredentialsValidator implements Validator {
    private final ClientService clientService;

    public FriendCredentialsValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean supports(Class<?> clazz) {
        return FriendCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            FriendCredentials friendCredentials = (FriendCredentials) target;
            String login = friendCredentials.getLogin();
            String currentLogin = friendCredentials.getCurrentLogin();
            if (clientService.isLoginVacant(friendCredentials.getLogin())) {
                errors.rejectValue("login", "login.does-not-exist", "client does not exist");
            } else if (login.equals(currentLogin)) {
                errors.rejectValue("login", "login.you-are-not-your-friend", "you are not your friend");
            } else if (clientService.findFriends(currentLogin, login) == null) {
                errors.rejectValue("login", "login.invalid-session", "invalid session");
            } else if (clientService.findFriends(currentLogin, login)) {
                errors.rejectValue("login", "login.you-are-already-friends", "you are already friends");
            }
        }
    }
}
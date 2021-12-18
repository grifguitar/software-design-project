package itmo.practice.form.validator;

import itmo.practice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import itmo.practice.form.FriendCredentials;

@RequiredArgsConstructor
@Component
public class FriendValidator implements Validator {
    private final ClientService clientService;

    public boolean supports(Class<?> clazz) {
        return FriendCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            FriendCredentials friendCredentials = (FriendCredentials) target;
            String login = friendCredentials.getLogin();
            String currentLogin = friendCredentials.getCurrentLogin();
            if (clientService.findFriends(login, currentLogin) == null) {
                errors.rejectValue("login", "login.invalid", "invalid");
            } else if (!clientService.findFriends(login, currentLogin)) {
                errors.rejectValue("login", "login.failed-you-are-not-his-friend", "failed, you are not his friend");
            }
        }
    }
}
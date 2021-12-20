package itmo.practice.form.validator;

import itmo.practice.form.ClientCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import itmo.practice.service.ClientService;

@RequiredArgsConstructor
@Component
public class ClientCredentialsRegisterValidator implements Validator {
    private final ClientService clientService;

    public boolean supports(Class<?> clazz) {
        return ClientCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            ClientCredentials registerForm = (ClientCredentials) target;
            if (!clientService.isLoginVacant(registerForm.getLogin())) {
                errors.rejectValue("login", "login.is-in-use", "login is in use already");
            }
        }
    }
}

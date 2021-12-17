package itmo.practice.form.validator;

import itmo.practice.form.ClientCredentials;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import itmo.practice.service.ClientService;

@Component
public class ClientCredentialsEnterValidator implements Validator {
    private final ClientService clientService;

    public ClientCredentialsEnterValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean supports(Class<?> clazz) {
        return ClientCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            ClientCredentials enterForm = (ClientCredentials) target;
            if (clientService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword()) == null) {
                errors.rejectValue("password", "password.invalid-login-or-password", "invalid login or password");
            }
        }
    }
}
package itmo.practice.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FriendCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-z]+", message = "expected lowercase latin letters")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String currentLogin;

    public String getCurrentLogin() {
        return currentLogin;
    }

    public FriendCredentials setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
        return this;
    }
}
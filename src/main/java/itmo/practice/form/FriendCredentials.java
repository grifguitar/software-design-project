package itmo.practice.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class FriendCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-z]+", message = "expected lowercase latin letters")
    private String login;

    private String currentLogin;

    public FriendCredentials setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
        return this;
    }
}

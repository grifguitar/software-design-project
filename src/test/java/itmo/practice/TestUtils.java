package itmo.practice;

import itmo.practice.form.ClientCredentials;
import itmo.practice.form.FriendCredentials;

public class TestUtils {
    public static final String LOGIN = "johndoe";

    public static ClientCredentials clientCredentialsByLogin(String login) {
        ClientCredentials credentials = new ClientCredentials();
        credentials.setLogin(login);
        credentials.setPassword(login);
        return credentials;
    }


    public static FriendCredentials friendCredentialsByLogin(String login, boolean equal) {
        FriendCredentials credentials = new FriendCredentials();
        credentials.setLogin(login);
        credentials.setCurrentLogin((equal ? "" : "current_") + login);
        return credentials;
    }
}

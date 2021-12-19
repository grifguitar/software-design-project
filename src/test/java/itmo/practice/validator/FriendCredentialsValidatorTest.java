package itmo.practice.validator;

import itmo.practice.TestUtils;
import itmo.practice.domain.Client;
import itmo.practice.form.ClientCredentials;
import itmo.practice.form.FriendCredentials;
import itmo.practice.form.validator.ClientCredentialsEnterValidator;
import itmo.practice.form.validator.FriendCredentialsValidator;
import itmo.practice.service.ClientService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;

import static itmo.practice.TestUtils.LOGIN;

public class FriendCredentialsValidatorTest {
    private AutoCloseable closeable;

    @Mock
    private ClientService clientService;

    private FriendCredentialsValidator validator;

    @Before
    public void before() {
        closeable = MockitoAnnotations.openMocks(this);
        validator = new FriendCredentialsValidator(clientService);
    }

    @Test
    public void testFriendCredentialsSucceedValidation() {
        FriendCredentials credentials = TestUtils.friendCredentialsByLogin(LOGIN, false);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(credentials.getLogin()))
                .thenReturn(false);
        Mockito.when(clientService.findFriends(credentials.getCurrentLogin(), credentials.getLogin()))
                .thenReturn(false);
        validator.validate(credentials, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void testFriendCredentialsFriendsValidation() {
        FriendCredentials credentials = TestUtils.friendCredentialsByLogin(LOGIN, false);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(credentials.getLogin()))
                .thenReturn(false);
        Mockito.when(clientService.findFriends(credentials.getCurrentLogin(), credentials.getLogin()))
                .thenReturn(true);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @Test
    public void testFriendCredentialsLoginExistenceValidation() {
        FriendCredentials credentials = TestUtils.friendCredentialsByLogin(LOGIN, false);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(credentials.getLogin()))
                .thenReturn(true);
        Mockito.when(clientService.findFriends(credentials.getCurrentLogin(), credentials.getLogin()))
                .thenReturn(null);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @Test
    public void testFriendCredentialsWithEqualsLoginsValidation() {
        FriendCredentials credentials = TestUtils.friendCredentialsByLogin(LOGIN, true);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(credentials.getLogin()))
                .thenReturn(false);
        Mockito.when(clientService.findFriends(credentials.getCurrentLogin(), credentials.getLogin()))
                .thenReturn(false);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @After
    public void after() throws Exception {
        closeable.close();
    }
}

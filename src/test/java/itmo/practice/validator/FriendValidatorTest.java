package itmo.practice.validator;

import itmo.practice.TestUtils;
import itmo.practice.form.FriendCredentials;
import itmo.practice.form.validator.FriendValidator;
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

public class FriendValidatorTest {
    private AutoCloseable closeable;

    @Mock
    private ClientService clientService;

    private FriendValidator validator;

    @Before
    public void before() {
        closeable = MockitoAnnotations.openMocks(this);
        validator = new FriendValidator(clientService);
    }

    @Test
    public void testFriendValidation() {
        FriendCredentials credentials = TestUtils.friendCredentialsByLogin(LOGIN, false);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.findFriends(credentials.getLogin(), credentials.getCurrentLogin()))
                .thenReturn(null);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());

        errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.findFriends(credentials.getLogin(), credentials.getCurrentLogin()))
                .thenReturn(false);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());

        errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.findFriends(credentials.getLogin(), credentials.getCurrentLogin()))
                .thenReturn(true);
        validator.validate(credentials, errors);
        Assert.assertFalse(errors.hasErrors());
    }

    @After
    public void after() throws Exception {
        closeable.close();
    }
}

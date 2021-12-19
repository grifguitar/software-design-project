package itmo.practice.validator;

import itmo.practice.TestUtils;
import itmo.practice.domain.Client;
import itmo.practice.form.ClientCredentials;
import itmo.practice.form.validator.ClientCredentialsEnterValidator;
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

public class ClientCredentialsEnterValidatorTest {
    private AutoCloseable closeable;

    @Mock
    private ClientService clientService;

    private ClientCredentialsEnterValidator validator;

    @Before
    public void before() {
        closeable = MockitoAnnotations.openMocks(this);
        validator = new ClientCredentialsEnterValidator(clientService);
    }

    @Test
    public void testClientValidation() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword()))
                .thenReturn(new Client());
        validator.validate(credentials, errors);
        Assert.assertFalse(errors.hasErrors());

        errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword()))
                .thenReturn(null);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @After
    public void after() throws Exception {
        closeable.close();
    }
}

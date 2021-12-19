package itmo.practice.validator;

import itmo.practice.TestUtils;
import itmo.practice.form.ClientCredentials;
import itmo.practice.form.validator.ClientCredentialsRegisterValidator;
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

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = WpApplication.class)
public class ClientCredentialsRegisterValidatorTest {
    private AutoCloseable closeable;

    @Mock
    private ClientService clientService;

    private ClientCredentialsRegisterValidator validator;

    @Before
    public void before() {
        closeable = MockitoAnnotations.openMocks(this);
        validator = new ClientCredentialsRegisterValidator(clientService);
    }

    @Test
    public void testVacantCredentials() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Errors errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(LOGIN)).thenReturn(true);
        validator.validate(credentials, errors);
        Assert.assertFalse(errors.hasErrors());

        errors = new DataBinder(credentials).getBindingResult();
        Mockito.when(clientService.isLoginVacant(LOGIN)).thenReturn(false);
        validator.validate(credentials, errors);
        Assert.assertTrue(errors.hasErrors());
    }

    @After
    public void after() throws Exception {
        closeable.close();
    }
}

package itmo.practice.service;

import itmo.practice.TestUtils;
import itmo.practice.domain.Client;
import itmo.practice.domain.Post;
import itmo.practice.form.ClientCredentials;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static itmo.practice.TestUtils.LOGIN;

public class ClientServiceTest {
    private static final int ITERATIONS = 10;

    private final MockClientRepository clientRepository = new MockClientRepository();
    private final MockPostRepository postRepository = new MockPostRepository();
    private final ClientService service = new ClientService(clientRepository, postRepository);

    @Test
    public void registerTest() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Client client = service.register(credentials);
        Assert.assertEquals(credentials.getLogin(), client.getLogin());
        Assert.assertNotNull(client.getPassword_sha());
        Assert.assertTrue(clientRepository.getClients().contains(client));
    }

    @Test
    public void isLoginVacantTest() {
        Assert.assertTrue(service.isLoginVacant(LOGIN));

        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        service.register(credentials);
        Assert.assertFalse(service.isLoginVacant(LOGIN));
    }

    @Test
    public void findByLoginAndPasswordTest() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Assert.assertNull(service.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword()));

        service.register(credentials);
        Assert.assertNotNull(service.findByLoginAndPassword(credentials.getLogin(), credentials.getPassword()));
    }

    @Test
    public void findByLoginTest() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Assert.assertNull(service.findByLogin(credentials.getLogin()));

        service.register(credentials);
        Assert.assertNotNull(service.findByLogin(credentials.getLogin()));
    }

    @Test
    public void findFriends() {
        ClientCredentials credentials1 = TestUtils.clientCredentialsByLogin(LOGIN + 1);
        ClientCredentials credentials2 = TestUtils.clientCredentialsByLogin(LOGIN + 2);

        Assert.assertNull(service.findFriends(null, credentials2.getLogin()));
        Assert.assertNull(service.findFriends(credentials1.getLogin(), null));

        Client client1 = service.register(credentials1);
        Client client2 = service.register(credentials2);
        Assert.assertEquals(false, service.findFriends(credentials1.getLogin(), credentials2.getLogin()));

        service.writeFriend(client1, client2);
        Assert.assertEquals(true, service.findFriends(credentials1.getLogin(), credentials2.getLogin()));
    }

    @Test
    public void findByIdTest() {
        ClientCredentials credentials = TestUtils.clientCredentialsByLogin(LOGIN);

        Assert.assertNull(service.findById(0L));

        Client client = service.register(credentials);
        Assert.assertNotNull(service.findById(client.getId()));
    }

    @Test
    public void findAllTest() {
        Assert.assertEquals(0, service.findAll().size());

        ArrayList<Client> clients = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            Client client = service.register(TestUtils.clientCredentialsByLogin(LOGIN + i));
            clients.add(0, client);
        }

        Assert.assertEquals(clients, service.findAll());
    }

    @Test
    public void writePostTest() {
        Client client = service.register(TestUtils.clientCredentialsByLogin(LOGIN));
        Post post = new Post();

        ArrayList<Client> clients = new ArrayList<>();
        clients.add(client);

        service.writePost(clients, post);

        Assert.assertTrue(client.getPosts().contains(post));
        Assert.assertTrue(postRepository.getPosts().contains(post));
    }

    @Test
    public void writeFriendTest() {
        Client client1 = service.register(TestUtils.clientCredentialsByLogin(LOGIN + 1));
        Client client2 = service.register(TestUtils.clientCredentialsByLogin(LOGIN + 2));

        Assert.assertFalse(client1.getFriends().contains(client2));

        service.writeFriend(client1, client2);

        Assert.assertTrue(client1.getFriends().contains(client2));
    }

    @After
    public void after() {
        clientRepository.deleteAll();
        postRepository.deleteAll();
    }
}

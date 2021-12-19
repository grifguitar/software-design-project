package itmo.practice.service;

import itmo.practice.domain.Post;
import itmo.practice.domain.Client;
import itmo.practice.form.ClientCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import itmo.practice.repository.PostRepository;
import itmo.practice.repository.ClientRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PostRepository postRepository;

    public Client register(ClientCredentials clientCredentials) {
        Client client = new Client();
        client.setLogin(clientCredentials.getLogin());
        clientRepository.save(client);
        clientRepository.updatePasswordSha(client.getId(), clientCredentials.getLogin(), clientCredentials.getPassword());
        return client;
    }

    public boolean isLoginVacant(String login) {
        return clientRepository.countByLogin(login) == 0;
    }

    public Client findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : clientRepository.findByLoginAndPassword(login, password);
    }

    public Client findByLogin(String login) {
        return login == null ? null : clientRepository.findByLogin(login);
    }

    public Boolean findFriends(String login1, String login2) {
        Client client1, client2;
        return ((client1 = clientRepository.findByLogin(login1)) == null ||
                (client2 = clientRepository.findByLogin(login2)) == null)
                ? null
                : (clientRepository.findFriends(client1.getId(), client2.getId()) != 0);
    }

    public Client findById(Long id) {
        return id == null ? null : clientRepository.findById(id).orElse(null);
    }

    public List<Client> findAll() {
        return clientRepository.findAllByOrderByIdDesc();
    }

    public void writePost(List<Client> clients, Post post) {
        postRepository.save(post);
        for (Client client : clients) {
            client.addPost(post);
            clientRepository.save(client);
        }
    }

    public void writeFriend(Client client, Client friend) {
        client.addFriend(friend);
        clientRepository.save(client);
    }
}
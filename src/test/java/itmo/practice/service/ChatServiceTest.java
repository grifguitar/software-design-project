package itmo.practice.service;

import itmo.practice.domain.Chat;
import itmo.practice.domain.Client;
import itmo.practice.domain.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ChatServiceTest {
    private static final int ITERATIONS = 10;

    private final MockChatRepository chatRepository = new MockChatRepository();
    private final ChatService service = new ChatService(chatRepository);

    @Test
    public void findAllTest() {
        Assert.assertEquals(0, service.findAll().size());

        ArrayList<Chat> chats = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            Chat chat = chatRepository.save(new Chat());
            chats.add(0, chat);
        }

        Assert.assertEquals(chats, service.findAll());
    }

    @Test
    public void findByIdTest() {
        Assert.assertNull(service.findById(0L));

        Chat chat = chatRepository.save(new Chat());

        Assert.assertNotNull(service.findById(chat.getId()));
    }

    @Test
    public void writeMessageTest() {
        Client client = new Client();
        Message message = new Message();
        Chat chat = chatRepository.save(new Chat());

        service.writeMessage(client, chat, message);

        Assert.assertTrue(chat.getMessages().contains(message));
    }

    @After
    public void after() {
        chatRepository.deleteAll();
    }
}

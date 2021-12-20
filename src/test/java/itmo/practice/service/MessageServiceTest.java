package itmo.practice.service;

import itmo.practice.domain.Client;
import itmo.practice.domain.Message;
import itmo.practice.domain.Chat;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MessageServiceTest {
    private static final int ITERATIONS = 10;

    private final MockMessageRepository messageRepository = new MockMessageRepository();
    private final MessageService service = new MessageService(messageRepository);

    @Test
    public void findAllByChat() {
        Chat chat = new Chat();
        chat.setMessages(new ArrayList<>());

        Assert.assertEquals(0, service.findAllByChat(chat).size());

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) {
            Message message = new Message();
            message.setId(i);
            chat.addMessage(new Client(), message);
            messages.add(0, message);
        }

        Assert.assertEquals(messages, service.findAllByChat(chat));
    }
}

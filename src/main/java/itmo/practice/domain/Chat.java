package itmo.practice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static itmo.practice.form.MessageCredentials.encode;

@Getter
@Setter
@Entity
@Table
public class Chat extends AbstractEntity {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Message> messages;

    public String getLastMessage() {
        if (messages.size() == 0) {
            return null;
        } else {
            Message message = messages.get(0);
            if (message != null) {
                return encode(message.getText());
            } else {
                return "NO MESSAGE";
            }
        }
    }

    @CreationTimestamp
    private Date creationTime;

    public void addMessage(Client client, Message message) {
        message.setChat(this);
        message.setClient(client);
        getMessages().add(message);
    }
}

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

import static itmo.practice.form.CommentCredentials.encode;

@Getter
@Setter
@Entity
@Table
public class Post extends AbstractEntity {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Comment> comments;

    public String getLastComment() {
        if (comments.size() == 0) {
            return null;
        } else {
            Comment comment = comments.get(0);
            if (comment != null) {
                return encode(comment.getText());
            } else {
                return "NO MESSAGE";
            }
        }
    }

    @CreationTimestamp
    private Date creationTime;

    public void addComment(Client client, Comment comment) {
        comment.setPost(this);
        comment.setClient(client);
        getComments().add(comment);
    }
}
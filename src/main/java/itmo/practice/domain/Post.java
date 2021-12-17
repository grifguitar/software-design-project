package itmo.practice.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static itmo.practice.form.CommentCredentials.encode;

@Entity
@Table
public class Post {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public String getLastComment() {
        if (comments.size() == 0) {
            return null;
        } else {
            return encode(comments.get(0).getText());
        }
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @CreationTimestamp
    private Date creationTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void addComment(Client client, Comment comment) {
        comment.setPost(this);
        comment.setClient(client);
        getComments().add(comment);
    }
}
package itmo.practice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        indexes = @Index(columnList = "creationTime"),
        uniqueConstraints = @UniqueConstraint(columnNames = "login")
)
public class Client extends AbstractEntity {
    @NotNull
    @NotEmpty
    private String login;

    private String password_sha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_post",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> posts;

    @CreationTimestamp
    private Date creationTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_role",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OrderBy("id desc")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client1_client2",
            joinColumns = @JoinColumn(name = "client1_id"),
            inverseJoinColumns = @JoinColumn(name = "client2_id")
    )
    private Set<Client> friends;

    public void addPost(Post post) {
        posts.add(post);
    }

    public void addFriend(Client friend) {
        getFriends().add(friend);
    }
}
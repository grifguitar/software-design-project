package itmo.practice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table
public class Comment extends AbstractEntity {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 650)
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String text;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
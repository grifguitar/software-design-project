package itmo.practice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Role extends AbstractEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    public Role() {
    }

    public Role(@NotNull Name name) {
        this.name = name;
    }

    public enum Name {
        WRITER,
        ADMIN
    }
}

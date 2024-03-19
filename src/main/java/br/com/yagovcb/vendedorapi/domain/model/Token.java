package br.com.yagovcb.vendedorapi.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "token")
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Column(nullable = false)
    private String token;

    @Column(name = "token_type", nullable = false)
    private String tokenType;

    @Column(nullable = false)
    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Usuario user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return getId() != null && Objects.equals(getId(), token.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
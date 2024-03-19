package br.com.yagovcb.vendedorapi.domain.model;

import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vendedor")
public class Vendedor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    @Size(min = 5, max = 15)
    @Column(name = "matricula", length = 15, unique = true, nullable = false)
    private String matricula;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotNull
    @Size(min = 11, max = 14)
    @Column(name = "documento", length = 14, nullable = false)
    private String documento;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contratacao", nullable = false)
    private TipoContracao tipoContratacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnoreProperties(value = { "vendedors" }, allowSetters = true)
    private Filial filial;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Vendedor vendedor = (Vendedor) o;
        return getId() != null && Objects.equals(getId(), vendedor.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "matricula = " + matricula + ", " +
                "nome = " + nome + ", " +
                "dataNascimento = " + dataNascimento + ", " +
                "documento = " + documento + ", " +
                "email = " + email + ", " +
                "tipoContratacao = " + tipoContratacao + ")";
    }
}

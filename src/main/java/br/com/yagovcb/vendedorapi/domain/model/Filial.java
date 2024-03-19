package br.com.yagovcb.vendedorapi.domain.model;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "filial")
public class Filial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Size(min = 14, max = 14)
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "cidade")
    private String cidade;

    @Size(min = 2, max = 2)
    @Column(name = "uf", length = 2)
    private String uf;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoFilial tipo;

    @Column(name = "ativo")
    private Boolean ativo;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ultima_atualizacao")
    private LocalDate ultimaAtualizacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filial")
    @JsonIgnoreProperties(value = { "filial" }, allowSetters = true)
    private Set<Vendedor> vendedores = new HashSet<>();

    public Filial addVendedor(Vendedor vendedor) {
        this.vendedores.add(vendedor);
        vendedor.setFilial(this);
        return this;
    }

    public Filial removeVendedor(Vendedor vendedor) {
        this.vendedores.remove(vendedor);
        vendedor.setFilial(null);
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Filial filial = (Filial) o;
        return getId() != null && Objects.equals(getId(), filial.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nome = " + nome + ", " +
                "cnpj = " + cnpj + ", " +
                "cidade = " + cidade + ", " +
                "uf = " + uf + ", " +
                "tipo = " + tipo + ", " +
                "ativo = " + ativo + ", " +
                "dataCadastro = " + dataCadastro + ", " +
                "ultimaAtualizacao = " + ultimaAtualizacao + ")";
    }
}

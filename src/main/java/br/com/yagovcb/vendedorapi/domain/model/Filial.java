package br.com.yagovcb.vendedorapi.domain.model;

import br.com.yagovcb.vendedorapi.domain.enums.TipoFilial;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static br.com.yagovcb.vendedorapi.utils.Utils.desformatarDocumento;
import static br.com.yagovcb.vendedorapi.utils.Utils.formatarCNPJ;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filial_seq")
    @SequenceGenerator(name = "filial_seq")
    @Column(name = "id")
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "filial_vendedores",
            joinColumns = @JoinColumn(name = "filial_id"),
            inverseJoinColumns = @JoinColumn(name = "vendedor_id")
    )
    private List<Vendedor> vendedores;

    public String getCnpj() {
        return formatarCNPJ(cnpj);
    }

    public void setCnpj(String cnpj) {
        this.cnpj = desformatarDocumento(cnpj);
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

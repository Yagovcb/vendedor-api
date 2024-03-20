package br.com.yagovcb.vendedorapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cadastro_vendedor_status")
public class CadastroVendedorStatus implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccadastro_vendedor_status_seq")
    @SequenceGenerator(name = "cadastro_vendedor_status_seq")
    private Long id;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "docuemento_vendedor", length = 20)
    private String documentoVendedor;

    @Column(name = "mensagem")
    private String mensagem;
}

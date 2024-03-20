package br.com.yagovcb.vendedorapi.utils;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.EmailException;
import br.com.yagovcb.vendedorapi.application.polices.PoliticaValidacaoEmail;
import br.com.yagovcb.vendedorapi.domain.enums.TipoContracao;
import br.com.yagovcb.vendedorapi.domain.model.Filial;
import br.com.yagovcb.vendedorapi.domain.model.Vendedor;
import br.com.yagovcb.vendedorapi.infrastructure.request.AtualizaVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.request.CadastroVendedorRequest;
import br.com.yagovcb.vendedorapi.infrastructure.response.VendedorResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VendedorUtils {
    public static VendedorResponse montaVendedorResponse(Vendedor vendedor, String filial) {
        return VendedorResponse.builder()
                .matricula(vendedor.getMatricula())
                .nome(vendedor.getMatricula())
                .dataNascimento(vendedor.getDataNascimento())
                .documento(vendedor.getDocumento())
                .email(vendedor.getEmail())
                .email(filial)
                .build();
    }

    public static Vendedor atualizaVendedor(Vendedor vendedor, AtualizaVendedorRequest atualizaVendedorRequest) {
        vendedor.setNome(String.valueOf(Utils.validaNaoNulo(atualizaVendedorRequest.getNome(), vendedor.getNome())));
        vendedor.setDocumento(String.valueOf(Utils.validaNaoNulo(atualizaVendedorRequest.getDocumento(), vendedor.getDocumento())));
        vendedor.setEmail(String.valueOf(Utils.validaNaoNulo(atualizaVendedorRequest.getEmail(), vendedor.getEmail())));
        return vendedor;
    }

    public static void validaEmail(String email){
        if (!PoliticaValidacaoEmail.validaEmail(email)){
            throw new EmailException(APIExceptionCode.NON_STANDARD_EMAIL, "O email passado não segue o padrão");
        }
    }

    public static Vendedor montaVendedorDefault(CadastroVendedorRequest cadastroVendedorRequest, Filial filial) {
        return Vendedor.builder()
                .nome(cadastroVendedorRequest.getNome())
                .matricula("ValorDefault")
                .dataNascimento(cadastroVendedorRequest.getDataNascimento())
                .documento(cadastroVendedorRequest.getDocumento())
                .email(cadastroVendedorRequest.getEmail())
                .tipoContratacao(cadastroVendedorRequest.getTipoContratacao())
                .filial(filial)
                .build();
    }

    public static void geraMatriculaVendedor(Vendedor vendedor) {
        vendedor.setMatricula(geraNumero(vendedor.getId()) + "-" + retornaTipoContratacao(vendedor.getTipoContratacao()));
    }

    private static String retornaTipoContratacao(TipoContracao tipoContratacao) {
        return switch (tipoContratacao) {
            case CLT -> "CLT";
            case PESSOA_JURIDICA -> "PJ";
            default -> "OUT";
        };
    }

    private static String geraNumero(Long id) {
        return String.format("%07d", id);
    }


}

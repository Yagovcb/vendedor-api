package br.com.yagovcb.vendedorapi.utils;

import br.com.yagovcb.vendedorapi.application.enums.APIExceptionCode;
import br.com.yagovcb.vendedorapi.application.exceptions.BusinessException;
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
                .nome(vendedor.getNome())
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

    public static void validaEmail(String email) throws EmailException{
        if (!PoliticaValidacaoEmail.validaEmail(email)){
            throw new EmailException(APIExceptionCode.NON_STANDARD_EMAIL, "O email passado não segue o padrão");
        }
    }

    public static Vendedor montaVendedorDefault(CadastroVendedorRequest cadastroVendedorRequest, Filial filial) {
        return Vendedor.builder()
                .nome(cadastroVendedorRequest.getNome())
                .matricula("ValorDefault")
                .dataNascimento(cadastroVendedorRequest.getDataNascimento())
                .documento(Utils.desformatarDocumento(cadastroVendedorRequest.getDocumento()))
                .email(cadastroVendedorRequest.getEmail())
                .tipoContratacao(cadastroVendedorRequest.getTipoContratacao())
                .filial(filial)
                .build();
    }

    public static void geraMatriculaVendedor(Vendedor vendedor) {
        vendedor.setMatricula(geraNumero(vendedor.getId()) + "-" + retornaTipoContratacao(vendedor.getTipoContratacao()));
    }

    public static boolean validaDocumentoVendedor(String documento) {
        Utils.desformatarDocumento(documento);
        // Verificar se contém apenas dígitos
        if (!documento.matches("\\d+")) {
            throw new BusinessException(APIExceptionCode.CONSTRAINT_VALIDATION, "Não são permitidas letras no documento");
        }

        // Validar CPF ou CNPJ
        if (documento.length() == 11) {
            return validarCPF(documento);
        } else if (documento.length() == 14) {
            return validarCNPJ(documento);
        } else {
            throw new BusinessException(APIExceptionCode.CONSTRAINT_VALIDATION, "O documento deve ter 11 caracteres (CPF) ou 14 caracteres (CNPJ)");
        }
    }

    // Método principal para validar um CPF
    public static boolean validarCPF(String cpf) {
        // Verifica se o CPF possui uma sequência de números iguais ou se tem tamanho diferente de 11
        if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                cpf.length() != 11) {
            return false;
        }

        // Cálculo do primeiro dígito verificador
        int soma = calculaDigitoVerificador(cpf, 0, 10);
        int digito1 = calcularDigitoCpf(soma);

        // Cálculo do segundo dígito verificador
        soma = calculaDigitoVerificador(cpf, 0, 11);
        int digito2 = calcularDigitoCpf(soma);

        // Compara os dígitos calculados com os dígitos verificadores do CPF
        return (digito1 == cpf.charAt(9) - '0') && (digito2 == cpf.charAt(10) - '0');
    }

    private static int calculaDigitoVerificador(String cpf, int soma, int peso) {
        for (int i = 0; i < (peso - 1); i++) {
            int numero = cpf.charAt(i) - '0'; // Converte o caractere para número inteiro
            soma += numero * peso;
            peso--;
        }
        return soma;
    }


    // Método auxiliar para calcular um dígito verificador com base na soma e pesos
    private static int calcularDigitoCpf(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    // Método principal para validar um CNPJ
    private static boolean validarCNPJ(String cnpj) {
        //validação dos cnpjs invalidos
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
                cnpj.length() != 14) {
            return false;
        }


        var dozePrimeirosNumeros = cnpj.substring(0, 12);
        //Calcula o primeiro digito verificador
        int digito1 = calcularDigitoCnpj(dozePrimeirosNumeros);

        //Calcula o segundo digito verificador
        int digito2 = calcularDigitoCnpj(dozePrimeirosNumeros + digito1);

        return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);
    }

    /**
     * Os dígitos verificadores são calculados usando os 12 primeiros dígitos do CNPJ.
     * Para isso, utilizamos um processo de multiplicação desses dígitos por uma série de pesos.
     *
     * @param cnpj Cnpj para usar na validação OBS.: No primeiro digito verificador, são usados apenas os 12 primeiros digitos
     *
     *
     * @return Se o resto for menor que 2, o primeiro dígito verificador é 0. Se for maior ou igual a 2,
     * subtraímos o resto de 11 para obter o dígito.
     * */
    private static int calcularDigitoCnpj(String cnpj) {
        int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int indice = cnpj.length() - 1; indice >= 0; indice--) {
            int digito = Integer.parseInt(cnpj.substring(indice, indice + 1));
            soma += digito * pesoCNPJ[pesoCNPJ.length - cnpj.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
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

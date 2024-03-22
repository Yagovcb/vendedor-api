
# Olá! Eu sou o Yago do Valle Castelo Branco :man_technologist:


### Sobre mim

#### I am a Senior Software Developer and Software Architect

- Meu nome é Yago, Sou um engenheiro de software e moro atualmente no Brasil
- Trabalho com tecnologia a quase 7 anos e possuo experiência com diversas tecnologias, principalmente Java e Flutter

##

<br>
<h1 align="center">
API CRUD Vendedor
</h1>
<br>

## 💬 Sobre esse repositorio

Este projeto implementa uma API backend para gerenciamento de dados no domínio de vendedores. Oferece funcionalidades CRUD padrão, permitindo a criação, consulta, atualização e remoção de registros de vendedores.

## Características Principais
- Criação de registros de vendedores.
- Consulta de detalhes de vendedores.
- Atualização de informações de vendedores.
- Remoção de registros de vendedores.


## ⚠ Pré requisitos do projeto

* Java 17 or higher versions
* Maven

## 📌 Como usar?

Para executar o projeto, digite o seguinte comando no diretório raiz:

```
mvn spring-boot:run 
```

A aplicação subirá por padrão na porta 8099, garanta que essa porta ja não esteja em uso!

## ⚠ Super importante

Esta API possui configuração Spring Security atualizada e está pronta para ser acoplada ao front-end. A metodologia de autenticação é através de tokens Barear com codificação JWT.

Para simplificar o processo, estamos utilizando um banco de dados H2 em memória com um usuário previamente cadastrado, as informações deste usuário estão localizadas na pasta de componentes, na pasta config.

Para gerar um token JWT, direcione a solicitação com os seguintes valores de usuario e senha:

```
{
    "username": "admin",
    "password": "password"
}
```

para o seguinte endpoint:
```
POST http://localhost:8099/api/v1/auth/signin
```

Guarde o token que será retornado pois você precisará dele para acessar os endpoints

## 📲 Serviços disponiveis para teste

### Busca todos os vendedores
```
Method: GET
URL: http://localhost:8099/api/v1/vendedor/
```

### Busque todos os vendedores com base no documento
```
Method: GET
URL: http://localhost:8099/api/v1/vendedor/{documento}
```
Documento deve ser passado no formato de texto, sendo aceito apenas CPF ou CNPJ

### Busque o status do cadastro do vendedor previamente cadastrado
```
Method: GET
URL: http://localhost:8099/api/v1/vendedor/{documento}/status
```
Documento deve ser passado no formato de texto, sendo aceito apenas CPF ou CNPJ

### Método de cadastramento do Vendedor
```
Method: POST
URL: http://localhost:8099/api/v1/vendedor/
Necessita de um Body na requisição no seguinte formato json:

{
  "nome": "Fulano da Silva",
  "data_nascimento": "yyyy-MM-dd",
  "documento": "{Numero de CPF ou CNPJ valido}",
  "email": "Email valido",
  "tipo_contratacao": "OUTSOURCING", //Pode ser ainda: CLT ou PESSOA_JURIDICA
  "filial_cnpj": "Consulte o enpoint de Filiais para saber qual usar"
}

```

### Método de Atualização do vendedor cadastrado
```
Method: PATCH
URL: http://localhost:8099/api/v1/vendedor/{documento}
Necessita de um Body na requisição no seguinte formato json:

{
  "nome": "Fulano da Silva",
  "documento": "{Numero de CPF ou CNPJ valido}",
  "email": "Email valido",
}
```
Documento deve ser passado no formato de texto, sendo aceito apenas CPF ou CNPJ

### Método de Deleção de um vendedor previamente cadastado
```
Method: DELETE
URL: http://localhost:8099/api/v1/vendedor/{documento}
```
Documento deve ser passado no formato de texto, sendo aceito apenas CPF ou CNPJ

### Método de Busca das filiais cadastradas
```
Method: GET
URL: http://localhost:8099/api/v1/filial/
```

##

Obrigado por testar minha API!

##
<h4><b><samp>Connect with me:</samp></b></h4>

[![Github Badge](https://img.shields.io/badge/-Github-000?style=flat-square&logo=Github&logoColor=white&link=https://github.com/Yagovcb)](https://github.com/Yagovcb)
[![Gmail](https://img.shields.io/badge/yago.vcb@hotmail.com-FFFEEE?style=flat-square&logo=gmail&logoColor=red)](mailto:yago.vcb@hotmail.com)
[![Twitter](https://img.shields.io/badge/@Yagovcb-1DA1F2?style=flat-square&logo=twitter&logoColor=white)](https://twitter.com/Yagovcb)
[![Linkedin](https://img.shields.io/badge/Yago_do_Valle_Castelo_Branco-0077b5?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/yagovcb/)
[![Medium](https://img.shields.io/badge/@yagovcb-black?style=flat-square&logo=medium&logoColor=white)](https://medium.com/@yagovcb)


![](https://visitor-badge.glitch.me/badge?page_id=Yagovcb.Yagovcb)

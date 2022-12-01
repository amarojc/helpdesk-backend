# Help Desk (back end)
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/amarojc/helpdesk-backend/blob/master/LICENCE) 

# Sobre o projeto

https://helpdesk-front-ecru.vercel.app

Helpdesk é uma aplicação full stack web utilizando Spring Boot 2.3.12, no Back end e o Angular 12, no [front end](https://github.com/amarojc/helpdesk-frontend).

A aplicação consiste em realizar o gerenciamento de chamados/ordens de serviço simulando um simples Help Desk.

De fácil compreensão a aplicação conta com um menu, na lateral esquerda, onde o usuário poderá navegar pelo sistema podendo criar, alterar, 
editar ou excluir um determinado técnico ou cliente, abrir um novo chamado, alterar o status do chamado e realizar filtros para localizar uma determinada informação.

Necessário que o usuário realize login com seu email e senha para ter acesso ao sistema e suas funcionalidade.

Somente pessoas com o perfil de ADM têm acesso para criar, atualizar e deletar um determinado técnico.

Técnicos e/ou clientes que possuem ordem de serviços/chamados não podem ser deletados.

É realizada a validação dos dados ao realizar um novo cadastro, no backend e no frontend.

## Modelo conceitual
![DC_HELPDESK](https://github.com/amarojc/helpdesk-front/blob/master/src/assets/img/DC_Helpdesk.png)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot 2.3.12 RELEASE
- Spring Data JPA
- Spring Security
- Spring MVC
- Hibernate
- Maven
- Autenticação e Autorização com Tokens JWT
- Banco de dados h2 em tempo de compilação
- Banco de dados MySQL
- Tratamento de exceções personalizada 
- Validações com Validations
- Desenvolvimento em camadas usando padrão MVC
- Implementado perfil de Teste e Desenvolvimento
- Protocolo HTTP no padrão REST
- Teste API REST usando a ferramenta Postman

## Front end
Acesse: https://github.com/amarojc/helpdesk-frontend

## Implantação em produção
- Back end: Heroku
- Front end web: Vercel
- Banco de dados: MySql

# Autor
Jorge Amaro de Carvalho

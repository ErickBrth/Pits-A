# pits-a-grupo9-psoft
- Recentemente, diversas empresas do ramo alimentício têm se desvinculado dos grandes aplicativos de delivery. As causas dessa tendência são diversas e vão desde a transformação no modo de operação de cada estabelecimento, até as taxas abusivas das grandes plataformas.
Porém, em 2022, simplesmente não é viável voltar ao modo de trabalho “pré-Ifood”... Foi por isso que a pizzaria Pits A decidiu desenvolver seu próprio aplicativo de delivery. E adivinha só… vocês foram escolhidos para ajudar!

# Grupo:
 - Erick Araken Vieira Gomes - [ErickBrth](https://github.com/ErickBrth)
 - João Paulo Ferreira Gomes - [joaopaulofg](https://github.com/joaopaulofg)
 - José Marinho Falcão Neto - [marinhofn](https://github.com/marinhofn)
 - Igorr Correia da Silva - [igor-correia](https://github.com/igor-correia)
 - Davyson Douglas Gomes Guimarães - [davysond](https://github.com/davysond)

## User Stories
### US1
- Eu, enquanto funcionário(a), quero utilizar o sistema para criar e editar o estabelecimento. 

### US2
- Eu, enquanto cliente, quero utilizar o sistema para me cadastrar como cliente do estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover clientes.

### US3
- Eu, enquanto funcionário(a) terceirizado(a), quero utilizar o sistema para me cadastrar como entregador(a) do estabalecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover entregadores.

### US4
- Eu, enquanto funcionário(a), quero utilizar o sistema para aprovar ou rejeitar entregadores do estabelecimento.

### US5
- Eu, enquanto funcionário(a), quero utilizar o sistema para o CRUD dos sabores de pizza vendidos pelo estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover sabores.

### US6
- Eu, enquanto cliente, quero visualizar o cardápio do estabelecimento.

### US7
- Eu, enquanto cliente, quero utilizar o sistema para fazer pedidos de pizza ao estabelecimento. Mais detalhadamente, deve ser possível criar, ler, editar e remover pedidos.

### US8
- Eu, enquanto funcionário(a), quero modificar a disponibilidade dos sabores. Mais detalhadamente, deve ser possível visualizar e editar a disponibilidade dos sabores de pizza — dado que, nem sempre, todos os produtos estão disponíveis.

### US9
- Eu, enquanto cliente, quero demonstrar interesse em sabores de pizza que não estão disponíveis no momento.
Os clientes devem ser capazes de demonstrar interesse apenas por sabores que se encontram indisponíveis.

### US10
- Eu, enquanto funcionário(a), quero disponibilizar diferentes meios de pagamento para os pedidos, tal que cada meio de pagamento também gere descontos distintos.

### US11
- Eu, enquanto funcionário(a), quero que o sistema garanta a corretude nas mudanças de status dos pedidos. 

### US12
- Eu, enquanto cliente, quero ser notificado(a) quando meus pedidos estiverem em rota e, por medidas de segurança, quero ser informado(a) com o nome do(a) entregador(a) responsável pela entrega e os detalhes sobre seu veículo. A notificação deve ser representada como uma mensagem no terminal da aplicação (print), indicando o motivo e quem está recebendo a notificação.
- 
### US13
- Eu, enquanto cliente, quero ser responsável por confirmar a entrega dos meus pedidos. 
O cliente será responsável por mudar o status de seus pedidos para “Pedido entregue”.

### US14
- Eu, enquanto funcionário(a), quero que o estabelecimento seja notificado(a) sempre que o status de um pedido for modificado para “Pedido entregue”. A notificação deve ser representada como uma mensagem no terminal da aplicação (print), indicando o motivo e quem está recebendo a notificação.

### US15
- Eu, enquanto cliente, quero ter a possibilidade de cancelar um pedido que fiz no estabelecimento.

### US16
- Eu, enquanto cliente, quero poder verificar os pedidos que já realizei no estabelecimento. 

### US17
- Eu, enquanto funcionário(a) terceirizado(a), desejo definir se estou disponível (ou não) para realizar as entregas do estabelecimento.
Apenas o(a) entregador(a) pode definir sua própria disponibilidade (em atividade ou em decanso).
 
### US18
- Eu, enquanto funcionário(a), gostaria que o sistema atribuísse automaticamente as entregas dos pedidos com status “Pedido Pronto” a um(a) entregador(a) que esteja disponível para realizar entregas.

## Tecnologias
Código base gerado via [start.sprint.io](https://start.spring.io/) com as seguintes dependências:  

- Spring Web
- Spring Actuator
- Spring Boot DevTools
- Spring Data JPA
- H2 Database
- Cucumber

## Endereços úteis

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/h2](http://localhost:8080/h2)

## Testes

<code>> mvn clean test </code>

## Diagram de Classes UML
- [Diagrama](https://lucid.app/lucidchart/e639f3ba-91dc-450e-9e85-a917e620b2da/edit?viewport_loc=2%2C-5%2C4561%2C1920%2CHWEp-vi-RSFO&invitationId=inv_93fa86e5-ad16-49c9-a945-6f79872bf695)




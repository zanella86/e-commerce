# SPORT CONNECTION - E-COMMERCE

---

# ENUNCIADO

Trata-se de um sistema de cadastro de produtos e pedidos em um portal de e-commerce. Considere
os requisitos:
- O portal possui vários produtos em estoque e com uma determinada quantidade em estoque de
cada um desses produtos.
- Cada pedido possui um ou mais produtos e um cliente associado a esse pedido.
- Um produto pode aparecer em um ou mais pedidos.
- Cada produto possui um código, um nome, uma quantidade e um valor.
- Cada cliente possui seus dados pessoais e dados de entrega.

Com base nestes requisitos:

1. Escrever o modelo do banco de dados (o famoso MER).
2. Definir as entidades necessárias.
3. Desenvolver uma aplicação, do tipo Java Project, onde todas as informações são fornecidas pelo
   usuário.
4. Utilizar o Maven ou Gradle para o gerenciamento de dependências.
5. Esse sistema deve ser desenvolvido utilizando uma das cinco combinações de técnicas:
   
   a. Spring Data JPA + Cache Redis (os dois em um único projeto). ✔
   
   b. Spring Data JPA (em um projeto separado) e Neo4J (em outro projeto separado).
   
   c. Spring Data JPA (em um projeto separado) e MongoDB (em outro projeto separado, sendo
   que neste projeto considere que pode existir um ou mais endereços de entrega
   cadastrados).
   
   d. Cassandra (em outro projeto separado, lembrando que o Cassandra não suporta
   relacionamentos, então a lógica associada a isso deverá ser implementada pela aplicação).
   
   e. Elaboração de Dockerfile ou Docker Compose referente a dois bancos de dados utilizados
   na disciplina, envolvendo o processo de clonagem de um repo até a disponibilização do
   ambiente (nesse caso basta construir um projeto básico para provar o conceito).

6. Documentar e justificar a escolha das técnicas, assim como as decisões técnicas e de arquiteturas
do sistema.

---

# OVERVIEW

- Fluxo
<!--
![FLUXO](docs/fluxo.PNG)
-->

- MER
<!--
![MER](docs/MER.PNG)
-->

---

# GRUPO

- RM346315: Lais Kagawa ([lakagawa](https://github.com/lakagawa))
- RM346511: Jônatha Lacerda Gonzaga ([jhowlacerda](https://github.com/jhowlacerda))
- RM346958: Thiago de Souza Zanella ([zanella86](https://github.com/zanella86))

<!--

---

# REPOSITÓRIO RELACIONADOS
-->

<!--

---

# FERRAMENTAS DE INTEGRAÇÃO

- Github
- RabbitMQ
- -->

---

# CONSTRUÇÃO

![Spring Initializr](docs/spring-initializr-setup-mysql.PNG)

# PARA TESTAR

- Crie um *database schema* no MySQL chamado `my-ecommerce`
![MySQL-Create-Schema](docs/mysql-schema-create.PNG)

---

# REFERÊNCIAS

- [FIAP (Profº Rafael Matsuyama)](https://github.com/rafaelmatsuyama/FIAP-2022-SCJ-JavaPersistence)
<!-- 
- [Emojis](https://github.com/markdown-templates/markdown-emojis)
-->

# Arquitetura #

A aplicação foi dividida em duas partes, backend e frontend, sendo que a comunicação entre elas se dá por meio de uma interface REST, portanto as partes estão bem desacopladas.

O projeto backend foi construído utilizando o framework spring-boot, e está preparado para uma arquitetura de microserviços privada ou na nuvem, podendo ser configurado para rodar em um container docker, na estrutura do OpenShift, Heroku ou outra plataforma semelhante. Outra possibilidade seria implantar o artefato em várias VMs e colocar um balanceamento por DNS, Apache ou outro meio, enfim, as possibilidades de escalabilidade horizontal são várias.<br>
Embora o projeto seja pequeno e com um modelo de dados bem simples, o projeto foi estruturado de forma que possa evoluir para um modelo mais complexo.<br>
Sendo assim foi criado uma camada "model" que contempla as entidades já mapeadas com o banco. A camada "repository" que está responsável por fazer todas as interações com banco, seja para consulta ou persistencia de dados. A camada "service" cuida da lógica de negócio da aplicação. E a camada "controller" está responsável por disponibilizar uma interface REST de acesso para a aplicação.<br>

Além das camadas citadas acima foi utilizado um DTO com Mapper para fazer a conversão dos objetos que serão expostos, embora para esse modelo não faça tanta diferença, em um modelo mais complexo geralmente o modelo de dados não é o mesmo modelo que será exposto. Para a conversão dos dados entre Entity e DTO foi utilizado a biblioteca mapstruct que tem como vantagem a simplicidade de implementação e o fato de não usar de reflection para fazer as conversões, já que as conversões são feitas em tempo de compilação com a criação de classes que implementam as interfaces desenvolvidas.<br>

Foi utilizado também o flyway que permite ter um maior controle sobre o banco de dados, já que este sofre muitas alterações ao longo do projeto. Além disso o flyway pode ajudar em uma eventual integração contínua do projeto, possibilitando que as alterações no banco possam ser feitas em todos os ambientes que fazem parte do fluxo juntamente com o deploy da aplicação.<br>

Para testes, foi realizado teste na camada de serviço, controller, conversão de objetos e também foi utilizado a biblioteca DBUnit que permite realizar testes integrados com banco, garantindo o funcionamento das camadas "service", "repository" e "model" integradas.<br>

Outra biblioteca utilizada foi o "swagger" que permite disponibilizar uma documentação que pode ser efetivamente testada da API da aplicação.

O projeto frontend foi construído utilizando o framework react, que permite construir a aplicação de forma componetizada. 
Como o objetivo do frontend é disponibilizar uma interface de fácil acesso para o desenvolvedor mobile, ele foi construído em apenas uma página e disponibiliza nessa página todas as operações CRUD necessárias.

<hr>

# Pré requisitos #

Os pre requisitos para instalar a aplicação são os seguintes:

- JDK 1.8
- maven 3.+
- npm
- MySQL

<hr>

# Testes #

Os testes da aplicação backend podem ser realizados das seguintes formas:
- **IDE:** Abrir o projeto em alguma IDE como Eclipse ou IntelliJ e executar os testes através da IDE.
- **maven:** Executar o comando ```$ mvn test``` dentro do diretorio backend

<hr>

# Instalação #

**Backend**

Para instalação do backend é necessário os seguintes passos
- **Criação do banco de dados:** Acesse o MySQL e execute o seguinte script:

```CREATE SCHEMA `zup` DEFAULT CHARACTER SET utf8 ;```

- **Alterar configuração de banco:** Acesse o arquivo ```application-local.properties``` que está dentro do diretório ```backend/src/main/resources``` e altere as propriedades de conexão de acordo com sua instalação local.
```json
# Database
...
spring.datasource.url=jdbc:mysql://localhost:3306/zup?useSSL=false
spring.datasource.username=root
spring.datasource.password=root
...

# FLYWAY
...
flyway.url=jdbc:mysql://localhost:3306/zup?useSSL=false
flyway.consumer=root
flyway.password=root
...
```
- **Gerar artefato:** Para gerar o artefato, dentro do diretório backend execute o seguinte comando:<br>
```$ mvn clean package```

- **Executar a aplicação:** Por fim para executar a aplicação, dentro do diretório backend execute o seguinte comando:<br>
```$ java -jar target/backend-0.0.1-SNAPSHOT.jar```


**Obs:** A aplicação vai subir na porta 8080, caso queira alterar a porta, mude a configuração no arquivo application-local.properties


**Frontend**

Para instalação do frontend execute os seguintes comandos dentro do diretorio frontend:

```
$ npm install
$ npm start
```
A aplicação frontend vai estar disponível no seguinte endereço: 

``` http://localhost:3000 ```

Caso deseje colocar a aplicação frontend em um servidor web como apache ou nginx por exemplo, execute o seguinte comando ```$ npm run build```. Será gerado um diretório chamado ```build/```, basta copiar esse diretório para o servidor web.

<hr>

# Documentação #

A documentação da API da aplicação pode ser visualizada e testada no seguinte endereço:

``` http://localhost:8080/zup/swagger-ui.html ```

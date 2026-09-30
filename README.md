# 🔹 Desafio Técnico Júnior #1 – Cadastro e Consulta de Abastecimentos

## 🛠 Objetivo

Desenvolver uma aplicação simples em **Java** para cadastro e consulta de abastecimentos em um posto de combustível, com armazenamento em banco de dados e exibição dos dados via **Java Swing** ou **API REST**.

---

## 📌 Funcionalidades Implementadas

✅ Operaçoes basicas (Criar, Listar, Alterar, Deletar) de **Tipos de Combustível** 
- Nome - Texto
- Preço por litro

✅ Operaçoes basicas (Criar, Listar, Alterar, Deletar) de **Bombas de Combustível** (relacionadas a um tipo de combustível)
- Nome da bomba
- Combustivel que abastece

✅ Operaçoes basicas (Criar, Listar, Alterar, Deletar)  de **Abastecimentos** (com data, volume abastecido e valor total)
- Bomba que foi realizado o abastecimento
- Data do abastecimento
- Quantidade em valores
- Litragem
  
✅ **Consulta** de todos os dados cadastrados (via Java Swing ou API)  
✅ Persistência dos dados (ao menos em tempo de execução)  

---

## ✅ Requisitos Atendidos

- Projeto Java com estrutura organizada (usando Maven ou Gradle)
- Relacionamentos entre entidades corretamente implementados
- Interface gráfica Java Swing **ou** API HTTP para cadastro e consulta
- Código comentado e organizado

---

## 🌟 Diferenciais Implementados

- API RESTful simples com rotas `GET`, `POST`, `PUT`
- Boas práticas de organização de código (DAO, camada de serviço, etc.)
- Persistencia dos dados (em caso de restart da aplicação manter os dados)
- 
---

## 📬 Como entregar o desafio

1. **Faça um fork** deste repositório.
2. Implemente a solução no seu fork.
3. Faça commits organizados com mensagens claras.
4. Após finalizar:
   - Envie o link do **repositório forkado** com a sua solução.
   - Certifique-se de que o projeto roda sem erros e que o README está atualizado.

---
## 🔍 O que será avaliado

- Sua **comunicação**, especialmente ao surgir dúvidas ou obstáculos durante o desenvolvimento.
- **O processo de desenvolvimento** como um todo, e não apenas o resultado final.
- A clareza e organização dos **commits** realizados.
- Sua capacidade de **estruturar a solução em etapas**, mesmo que nem todos os requisitos sejam concluídos.

---

## 💡 Dicas para se sair bem

- Divida o desafio em **pequenas partes** e implemente **com calma**, focando em cada funcionalidade por vez.
- Use **commits claros e objetivos**, indicando exatamente o que foi alterado ou implementado.
- Em caso de dúvida, **comunique-se** — mostrar que você sabe buscar soluções é um ponto positivo.
- Mesmo que não finalize 100% dos requisitos, **a qualidade do seu processo será levada em conta**.

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.16**
- **Spring Web**, **Spring Data JPA**, **Spring Validation**
- **H2 Database** (em memória, runtime)
- **Lombok**
- **ModelMapper**
- **SpringDoc OpenAPI** (Swagger UI)
- **Maven** para gerenciamento de dependências e build

## 📂 Estrutura do Projeto

```
src/main/java/br/com/gpfaltz/posto_combustivel/
├─ config/          → OpenApiConfig (Swagger)
├─ controller/      → CombustivelController, BombaController, AbastecimentoController
├─ dto/
│   ├─ request/    → CombustivelRequest, BombaRequest, AbastecimentoRequest
│   └─ response/   → CombustivelResponse, BombaResponse, AbastecimentoResponse
├─ entity/          → Combustivel, Bomba, Abastecimento (JPA)
├─ exception/      → GlobalExceptionHandler, ResourceNotFoundException
├─ repository/     → JPA repositories (CombustivelRepository, BombaRepository, AbastecimentoRepository)
├─ service/         → Camada de negócio (CombustivelService, BombaService, AbastecimentoService)
└─ PostoAbastecimentoApplication.java (classe principal)
```

## 📋 Pré‑requisitos

- JDK **17** instalado e configurado no `PATH`.
- **Maven 3.9+** instalado.
- (Opcional) IDE como IntelliJ IDEA, VS Code ou Eclipse.

## ⚙️ Configuração do Projeto

O projeto utiliza o **Spring Boot** com configuração padrão. As dependências estão declaradas em `pom.xml`, incluindo:
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `h2` (banco em memória)
- `lombok`
- `modelmapper`
- `springdoc-openapi-starter-webmvc-ui`

Não há necessidade de arquivos `application.properties` adicionais; o Spring Boot cria o datasource H2 automaticamente. Caso queira acessar o console H2, habilite em `src/main/resources/application.properties`:
```
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```

## ▶️ Como rodar localmente

```bash
# Clonar o repositório
git clone https://github.com/gpfaltz/posto-combustivel.git
cd posto-combustivel

# Compilar e executar
mvn clean install
mvn spring-boot:run
# ou, usando o jar gerado
java -jar target/posto-combustivel-0.0.1-SNAPSHOT.jar
```
A aplicação será iniciada em `http://localhost:8080`.

## 🧪 Como testar

### Swagger UI
Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

### Exemplos de requisições cURL
#### Combustível
```bash
# Criar
curl -X POST http://localhost:8080/api/combustiveis \
  -H "Content-Type: application/json" \
  -d '{"nome":"Álcool","precoPorLitro":3.59}'

# Listar todos
curl http://localhost:8080/api/combustiveis

# Buscar por ID (ex.: 1)
curl http://localhost:8080/api/combustiveis/1

# Atualizar (ex.: 1)
curl -X PUT http://localhost:8080/api/combustiveis/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Álcool","precoPorLitro":3.79}'

# Deletar (ex.: 1)
curl -X DELETE http://localhost:8080/api/combustiveis/1
```
#### Bomba
```bash
# Criar (assumindo que o combustível com id 1 existe)
curl -X POST http://localhost:8080/api/bombas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Bomba 1","combustivelId":1}'

# Listar todos
curl http://localhost:8080/api/bombas

# Buscar por ID (ex.: 1)
curl http://localhost:8080/api/bombas/1

# Atualizar (ex.: 1)
curl -X PUT http://localhost:8080/api/bombas/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Bomba 1 Atualizada","combustivelId":1}'

# Deletar (ex.: 1)
curl -X DELETE http://localhost:8080/api/bombas/1
```
#### Abastecimento
```bash
# Criar (assumindo bomba id 1)
curl -X POST http://localhost:8080/api/abastecimentos \
  -H "Content-Type: application/json" \
  -d '{"bombaId":1,"data":"2023-01-01","volume":50.0}'

# Listar todos
curl http://localhost:8080/api/abastecimentos

# Buscar por ID (ex.: 1)
curl http://localhost:8080/api/abastecimentos/1

# Atualizar (ex.: 1)
curl -X PUT http://localhost:8080/api/abastecimentos/1 \
  -H "Content-Type: application/json" \
  -d '{"bombaId":1,"data":"2023-01-02","volume":55.0}'

# Deletar (ex.: 1)
curl -X DELETE http://localhost:8080/api/abastecimentos/1
```

### Testes automatizados
Execute os testes unitários (se houver) com:
```bash
mvn test
```
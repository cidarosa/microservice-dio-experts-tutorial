# 🌟 DIO experts: Construindo um Projeto com Arquitetura Baseada em Microsserviços Usando Spring Cloud - Versão atualizada

Este projeto é a implementação do tutorial da **DIO**, com todas as dependências e configurações **atualizadas** para o ambiente moderno do **Spring Boot 3.4.11** e o **Elasticsearch 8.x**.

---

## 🏗️ Arquitetura e Módulos

O projeto segue a arquitetura de microsserviços e é composto pelos seguintes módulos principais:

* **`config-server`** (Porta **8888**): Módulo central para **configuração unificada**. Busca configurações (`product-catalog.yml`, `shopping-cart.yml`, etc.) de um repositório Git para os `microservices`.
* **`eureka-server`** (Porta **8761**): Módulo central para **descoberta de serviços (Service Discovery)**. Permite que os `microservices` se registrem e se encontrem dinamicamente.
* **`api-gateway`** (Porta **8080**): **Serviço** que roteia **todas as requisições externas** para o `microservice` correto, além de ser o ponto de controle para segurança e filtros.
* **`product-catalog`** (Porta **8081**): Responsável pelo gerenciamento do catálogo de produtos, indexação no `Elasticsearch` e exposição das `APIs REST`.
* **`shopping-cart`** (Porta **8082**): Responsável pela gestão do carrinho de compras e utiliza o `Redis` como cache distribuído.

---

## 🛠️ Tecnologias Principais

| Componente          | Versão Utilizada         | Finalidade                                                 |
|:--------------------|:-------------------------|:-----------------------------------------------------------|
| **Framework**       | Spring Boot **3.4.11**   | Core da aplicação (API REST).                              |
| **Service Discovery** | Spring Cloud Eureka      | Registro dinâmico de todos os microservices.                 |
| **Config Server**   | Spring Cloud Config      | Centralização das configurações (buscadas do Git).          |
| **API Gateway**     | Spring Cloud Gateway     | Roteamento de requisições e centralização de filtros.        |
| **Build Tool**      | Gradle                   | Gerenciamento de dependências e execução.                  |
| **Indexação/Busca** | Elasticsearch **8.13.4** | Armazenamento e busca de alta performance para o catálogo. |
| **Cache**           | Redis **7.x**            | Cache distribuído para acelerar consultas.                 |
| **Monitoramento**   | Spring Boot Actuator     | Health checks e métricas em tempo real.                    |

---

## 🐳 Configuração do Ambiente de Serviços (Docker Compose)

O ambiente de desenvolvimento requer dois serviços externos que são iniciados via Docker Compose.

**Atenção:** A versão do Elasticsearch foi atualizada de 6.x (tutorial) para **8.13.4** para garantir a compatibilidade com o **Spring Boot 3.x**.

### `docker-compose.yml`

```yaml
version: '3.8'

services:

  elasticsearch:
    container_name: "elasticsearch"
    # Versão compatível com Spring Boot 3.x e Spring Data Elasticsearch 5.x
    image: docker.elastic.co/elasticsearch/elasticsearch:8.13.4 
    ports:
      - 9200:9200
      - 9300:9300
    environment:
      # Configura como nó único (desenvolvimento)
      - discovery.type=single-node
      # Desabilita o requisito de segurança do ES 8.x para desenvolvimento local
      - xpack.security.enabled=false

  redis:
    container_name: "redis"
    image: redis:7.2.4-alpine 
    ports:
      - 6379:6379
```

### Como Iniciar o Ambiente


Certifique-se de que o Docker está em execução.

No terminal, na pasta raiz do arquivo docker-compose.yml, execute:

```bash
docker compose up -d
```

## 💻 Módulos e Ordem de Execução

Você deve iniciar os módulos de infraestrutura antes dos módulos de domínio.

1. **Config Server**: (Porta **8888**)
2. **Eureka Server**: (Porta **8761**)
3. **Product Catalog**: (Porta **8081**)
4. **Shopping Cart**: (Porta **8082**)
5. **API Gateway**: (Porta **8080**)

### Exemplo de Execução (`product-catalog`)
Navegue para o módulo `product-catalog` no IntelliJ e utilize o painel do **Gradle** para executar a **task** `bootRun`.

Alternativamente, execute via terminal na pasta do módulo:

```bash
# Na pasta do módulo product-catalog
../gradlew bootRun
```

## 🩺 Verificação e Monitoramento

Após a inicialização do `product-catalog`, podemos verificar o `status` dos serviços por meio do **Spring Boot Actuator**.

| Endpoint  |  Status Esperado  |   Detalhe  |
|:--------- |:----------------- |:---------- |
| http://localhost:8081/actuator/health |UP | Status geral da aplicação. |
| http://localhost:8081/actuator/health/elasticsearch | UP | Confirma conexão com o ES (porta 9200). |
| http://localhost:8081/actuator/health/redis | UP | Confirma conexão com o Redis (porta 6379).|
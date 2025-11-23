# 🌟 DIO experts: Construindo um Projeto com Arquitetura Baseada em Microsserviços Usando Spring Cloud

Este projeto é a implementação do tutorial da DIO, com todas as dependências e configurações **atualizadas** para o ambiente moderno do **Spring Boot 3.4.11** e o **Elasticsearch 8.x**.

## 🏗️ Arquitetura e Módulos

O projeto segue a arquitetura de microsserviços e é composto pelos seguintes módulos principais:

* **`product-catalog`**: (Módulo Principal) Responsável pelo gerenciamento do catálogo de produtos, indexação no Elasticsearch e exposição das APIs REST.

## 🛠️ Tecnologias Principais

| Componente          | Versão Utilizada         | Finalidade                                                 |
|:--------------------|:-------------------------|:-----------------------------------------------------------|
| **Framework**       | Spring Boot **3.4.11**   | Core da aplicação (API REST).                              |
| **Build Tool**      | Gradle                   | Gerenciamento de dependências e execução.                  |
| **Indexação/Busca** | Elasticsearch **8.13.4** | Armazenamento e busca de alta performance para o catálogo. |
| **Cache**           | Redis **7.x**            | Cache distribuído para acelerar consultas.                 |
| **Monitoramento**   | Spring Boot Actuator     | Health checks e métricas em tempo real.                    |

---

## 🐳 Configuração do Ambiente de Serviços (Docker Compose)

O ambiente de desenvolvimento requer dois serviços externos que são iniciados via Docker Compose.

**Atenção:** A versão do Elasticsearch foi atualizada de 6.x (tutorial) para **8.13.4** para garantir a compatibilidade com o Spring Boot 3.x.

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

***
Como Iniciar o Ambiente
Certifique-se de que o Docker está em execução.

No terminal, na pasta raiz (DIO experts), execute:

docker compose up -d

***

💻 Módulo: product-catalog



2. Execução da Aplicação
Após o Docker estar ativo, navegue para o módulo product-catalog no seu IntelliJ e utilize o painel do Gradle para executar a task bootRun.

Alternativamente, execute via terminal na pasta do módulo:

# Na pasta do módulo product-catalog
../gradlew bootRun

***
🩺 Verificação e Monitoramento
Após a inicialização do product-catalog, você pode verificar o status dos serviços via Spring Boot Actuator:

| Endpoint	 | Status Esperado	 |   Detalhe  |
| :--- | :--- | :--- |
| http://localhost:8080/actuator/health |	UP	| Status geral da aplicação. |
| http://localhost:8080/actuator/health/elasticsearch |	UP	| Confirma conexão com o ES (porta 9200). |
| http://localhost:8080/actuator/health/redis |	UP	| Confirma conexão com o Redis (porta 6379). |
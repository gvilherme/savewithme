# SaveWithMe

Aplicativo de **poupança colaborativa**, inspirado em apps como Splitwise — mas com foco em **poupar juntos** em vez de dividir contas.  
Cada meta tem um valor estipulado (ex.: viagem, bem material) e o sistema acompanha o progresso dos depósitos feitos por cada participante.

---

## 📐 Arquitetura

O projeto segue **Arquitetura Hexagonal (Ports & Adapters)** em Java + Spring Boot:

- **Domain** → entidades e regras de negócio (`User`, `Goal`, `Deposit`).
- **Application** → casos de uso e orquestração de lógica.
- **Adapters In** → REST Controllers expondo os casos de uso.
- **Adapters Out** → persistência (Postgres via JPA), autenticação, etc.
- **Bootstrap** → camada que inicia o app e integra tudo no Spring.

### Infraestrutura
- **Docker Compose**: orquestra o `app` (Spring Boot) + `db` (Postgres).
- **CI/CD (GitHub Actions)**:
  - Testes unitários + lint.
  - Build & push da imagem no **ECR**.
  - Deploy com **Terraform** em uma EC2 t4g.micro (Free Tier).
- **Terraform**:
  - EC2 com `user_data` para instalar Docker/Compose e subir o app.
  - S3 para state remoto do Terraform.
  - S3 para backups (`pg_dump`).
  - EventBridge + Lambda para desligar/ligar a instância em horários definidos (economia de custo).

---

## 🚀 Rodando localmente

### Pré-requisitos
- Docker + Docker Compose
- Java 21 (Temurin/Corretto)
- Maven 3.9+

### Opção 1 — Rodar só o banco no Docker e app na IDE
```bash
cd ./infra/docker
docker compose up -d db

cd ../../app/bootstrap/target
java -jar bootstrap.jar -Dspring.profiles.active=local
```


### Opção 2 - Rodar db + app no Docker
```bash
cd ./infra/docker
docker compose -f compose.yaml up -d --build
```
---

### 🧑‍💻 Desenvolvimento

- O projeto é modular: cada pasta em `app/` representa um módulo Maven (domain, application, adapters, bootstrap).
- Para rodar testes:
  ```bash
  cd app
  mvn test
  ```
- Para rodar localmente com hot reload, use sua IDE (IntelliJ, VSCode) e configure o profile `local`.

### 🐳 Docker

- O Compose orquestra banco e app.
  - `db` usa Postgres 16, credenciais padrão:
    - user: `savewithme`
    - password: `savewithme`
  - O app sobe na porta 8080.
- Para acessar o banco localmente:
  ```
  psql -h localhost -U savewithme -d savewithme
  ```

### ☁️ CI/CD & Deploy

- O pipeline executa testes, lint, build, push da imagem para ECR e deploy via Terraform.
- O deploy cria uma EC2 (Free Tier) e sobe o app via Docker Compose.
- O state do Terraform é armazenado em S3 (bucket configurado via variável `TF_STATE_BUCKET`).

### 🔒 Segurança

- As credenciais AWS são gerenciadas via OIDC no GitHub Actions.
- O acesso ao bucket de backup é restrito por políticas IAM.

### 🛠️ Troubleshooting

- Se o app não subir, verifique os logs do container:
  ```
  docker compose logs app
  ```
- Se o banco não conectar, confira se a porta 5432 está liberada e se o SG permite acesso da EC2.

### 📄 Documentação Adicional

- Os arquivos de configuração do Terraform estão em `infra/terraform/`.
- O script de inicialização da EC2 está em `infra/terraform/user_data.sh.tftpl`.
- Para restaurar backup do banco, use o script `pg_backup_s3.sh` gerado na EC2.


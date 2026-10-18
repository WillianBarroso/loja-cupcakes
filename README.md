# Projeto Integrador Transdisciplinar em Engenharia de Software II

# 🧁 Loja de Cupcakes

Aplicação web full-stack desenvolvida em Java com Spring Boot e Thymeleaf para exibição, gerenciamento de vitrine de
cupcakes e sistema de carrinho de compras.

---

## 🛠️ Tecnologias Utilizadas

### Back-end

- **Java 17**
- **Spring Boot 3.x / 4.x**
    - Spring MVC (Controllers e Web)
    - Spring Data JPA (Persistência)
    - Spring DevTools (Hot reload em desenvolvimento)
- **Hibernate** (ORM)
- **Lombok** (Redução de código boilerplate)

### Banco de Dados

- **H2 Database** (Banco em memória para desenvolvimento local)
- **PostgreSQL** (Banco relacional para ambiente de produção)

### Front-end

- **Thymeleaf** (Engine de template Java)
- **Bootstrap 5** (Layout e componentes responsivos)
- **CSS3 / JavaScript** (Estilização personalizada dark-theme e controles dinâmicos)

### Infraestrutura & Deploy

- **Docker & Dockerfile** (Containerização)
- **Gradle** (Gerenciador de dependências e build)
- **Render** (Hospedagem da aplicação e do banco PostgreSQL)

---

## 🚀 Como Rodar o Projeto Localmente

### Pré-requisitos

- **Java 17** instalado.
- **Gradle** instalado (ou utilize o wrapper `./gradlew`).
- IDE de sua preferência (ex: **IntelliJ IDEA**).

### Passos

1. **Clonar o repositório:**
   ```bash
   git clone [https://github.com/WillianBarroso/loja-cupcakes.git](https://github.com/WillianBarroso/loja-cupcakes.git)
   cd loja-cupcakes
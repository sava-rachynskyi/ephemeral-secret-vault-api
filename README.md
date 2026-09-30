# Ephemeral Secret Vault API

A secure, high-performance REST API service for sharing sensitive, single-use ephemeral secrets. Designed with a **Burn-on-Read** policy ensuring that secrets are strictly erased from RAM and database immediately upon initial access or expiration.

---

## Tech Stack

* **Java 21** & **Spring Boot 3.4+**
* **PostgreSQL 16** (Metadata storage & Liquibase migrations)
* **Redis 7** (In-memory encrypted secret content engine with TTL)
* **MapStruct & Lombok** (Compile-time DTO mappings)
* **Docker & Docker Compose** (Containerized orchestration)
* **Testcontainers** (Isolated integration testing)

---

## Getting Started

### Prerequisites
* Docker Desktop installed and running
* Git

### Quick Start with Docker Compose

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/sava-rachynskyi/ephemeral-secret-vault-api.git](https://github.com/sava-rachynskyi/ephemeral-secret-vault-api.git)
   cd ephemeral-secret-vault-api
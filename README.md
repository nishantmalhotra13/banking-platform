# Banking Platform

A distributed microservices banking platform built with **Spring Boot 3.4**, **Java 21**, and **Angular 17**.

## Architecture

```
┌──────────────┐     ┌──────────────────────┐
│  Angular UI  │────▶│    API Gateway :8080  │
│   :4200      │     │  (Spring Cloud GW MVC)│
└──────────────┘     └─────────┬────────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
   ┌──────────▼───┐  ┌────────▼──────┐  ┌──────▼──────────┐
   │ Orch Auth    │  │ Orchestrator  │  │ Orchestrator     │
   │ :8085 (JWT)  │  │ :8086         │  │ (Redis :6379)    │
   └──────────────┘  └───────┬───────┘  └─────────────────┘
                             │
                    ┌────────┴────────┐
                    │                 │
         ┌─────────▼──┐    ┌────────▼───────┐
         │ MDM Service │    │ CCSL Service   │
         │ :8084       │    │ :8082          │
         │ (PG :5433)  │    │ (PG :5434)     │
         └──────┬──────┘    └──────┬─────────┘
                │                  │
         ┌──────▼──┐        ┌─────▼─────┐
         │MDM Auth │        │CCSL Auth  │
         │ :8083   │        │ :8081     │
         └─────────┘        └───────────┘
```

## Full Infrastructure Diagram (Docker / Kubernetes)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              KUBERNETES CLUSTER (local / cloud)                          │
│                                                                                         │
│  ┌──────────────────────────────────────────────────────────────────────────────────┐    │
│  │                              NAMESPACE: banking-platform                         │    │
│  │                                                                                  │    │
│  │  ┌─────────────────────────────── INGRESS ──────────────────────────────┐    │    │
│  │  │                                                                          │    │    │
│  │  │  ┌──────────────────┐                                                    │    │    │
│  │  │  │   Angular UI     │  ← runs in YOUR BROWSER (not a server pod)         │    │    │
│  │  │  │   :4200 (dev)    │  Production: served by Nginx as static files       │    │    │
│  │  │  │   or Nginx :80   │                                                    │    │    │
│  │  │  └────────┬─────────┘                                                    │    │    │
│  │  │           │                                                              │    │    │
│  │  │  ┌────────▼─────────┐                                                    │    │    │
│  │  │  │  API Gateway     │  Spring Cloud Gateway MVC                          │    │    │
│  │  │  │  :8080           │  JWT validation, CORS, routing, Swagger proxy      │    │    │
│  │  │  │  1 replica       │                                                    │    │    │
│  │  │  └──┬──────┬───┬────┘                                                    │    │    │
│  │  └─────┼──────┼───┼─────────────────────────────────────────────────────────┘    │    │
│  │        │      │   │                                                              │    │
│  │  ┌─────▼──┐   │   └──────────────────────────────────┐                           │    │
│  │  │Orch    │   │                                      │                           │    │
│  │  │Auth    │   │                                      │                           │    │
│  │  │:8085   │   │                                      │                           │    │
│  │  │1 rep   │   │                                      │                           │    │
│  │  │(JWT)   │   │                                      │                           │    │
│  │  └──┬─────┘   │                                      │                           │    │
│  │     │    ┌────▼──────────────────────┐               │                           │    │
│  │     │    │  Orchestrator Service     │               │                           │    │
│  │     │    │  :8086  ×2 replicas       │               │                           │    │
│  │     │    │  StepExecutor Pipeline    │               │                           │    │
│  │     │    │  Virtual Threads          │               │                           │    │
│  │     │    │  Micrometer + Prometheus  │               │                           │    │
│  │     │    └───┬────────────┬─────────┘               │                           │    │
│  │     │        │            │                          │                           │    │
│  │     │   ┌────▼────┐  ┌───▼──────┐                   │                           │    │
│  │     │   │MDM      │  │CCSL      │                   │                           │    │
│  │     │   │Service  │  │Service   │                   │                           │    │
│  │     │   │:8084    │  │:8082     │                   │                           │    │
│  │     │   │×2 rep   │  │×2 rep    │                   │                           │    │
│  │     │   └──┬──┬───┘  └──┬──┬────┘                   │                           │    │
│  │     │      │  │         │  │                         │                           │    │
│  │     │   ┌──▼──┘      ┌──▼──┘                         │                           │    │
│  │     │   │MDM  │      │CCSL │                         │                           │    │
│  │     │   │Auth │      │Auth │                         │                           │    │
│  │     │   │:8083│      │:8081│                         │                           │    │
│  │     │   └─────┘      └─────┘                         │                           │    │
│  │     │                                                │                           │    │
│  │  ┌──┼────────────────── DATA LAYER ──────────────────┼──────────────────────┐    │    │
│  │  │  │  ┌──────────────┐  ┌──────────────┐  ┌────────┴───────┐              │    │    │
│  │  │  │  │ PostgreSQL   │  │ PostgreSQL   │  │ PostgreSQL     │              │    │    │
│  │  │  │  │ (MDM) :5433  │  │ (CCSL) :5434 │  │ (Auth) :5435   │              │    │    │
│  │  │  │  └──────────────┘  └──────────────┘  └────────────────┘              │    │    │
│  │  │  │  ┌──────────────────────────────┐                                    │    │    │
│  │  │  │  │          Redis :6379         │  Shared cache (1 replica)          │    │    │
│  │  │  │  └──────────────────────────────┘                                    │    │    │
│  │  └──┼──────────────────────────────────────────────────────────────────────┘    │    │
│  │     │                                                                           │    │
│  │  ┌──┼───────────── OBSERVABILITY (ELK Stack) ─────────────────────────────┐    │    │
│  │  │  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │    │    │
│  │  │  │  │Elasticsearch │◀─│  Logstash    │  │   Kibana     │              │    │    │
│  │  │  │  │ :9200        │  │  :5000       │  │   :5601      │              │    │    │
│  │  │  │  │ banking-logs │  │  (TCP JSON)  │  │  (Dashboard) │              │    │    │
│  │  │  │  └──────────────┘  └──────────────┘  └──────────────┘              │    │    │
│  │  └──┼──────────────────────────────────────────────────────────────────────┘    │    │
│  │     │                                                                           │    │
│  └─────┼───────────────────────────────────────────────────────────────────────────┘    │
└────────┼────────────────────────────────────────────────────────────────────────────────┘
```

## Port Map

| Service              | Port  | Notes                           |
|---------------------|-------|---------------------------------|
| API Gateway          | 8080  | Single entry point for all APIs |
| CCSL Auth            | 8081  | Internal (not exposed in Docker)|
| CCSL Service         | 8082  | Internal (not exposed in Docker)|
| MDM Auth             | 8083  | Internal (not exposed in Docker)|
| MDM Service          | 8084  | Internal (not exposed in Docker)|
| Orchestrator Auth    | 8085  | Exposed (login/register)        |
| Orchestrator Service | 8086  | Internal (not exposed in Docker)|
| Angular UI           | 4200  | `ng serve` dev server (browser) |
| Redis                | 6379  |                                 |
| PostgreSQL (MDM)     | 5433  |                                 |
| PostgreSQL (CCSL)    | 5434  |                                 |
| PostgreSQL (Auth)    | 5435  |                                 |
| Elasticsearch        | 9200  |                                 |
| Kibana               | 5601  |                                 |
| Logstash             | 5044  | Host port (maps to 5000 inside) |

## Quick Start

### Prerequisites
- Java 21 (Temurin)
- Docker Desktop
- Node.js 20+ (only for UI development)

### Option 1: Full Docker Stack (recommended)

```bash
make all                    # clean → build → docker-build → docker-up
```

If containers are already running, it will warn you and stop:
```
⚠️  Banking-platform containers are already running!
👉 Please run 'make docker-down' first, then retry.
```

### Option 2: Local Development (IntelliJ)

```bash
make infra-up               # start databases + Redis in Docker
# Then run each service from IntelliJ with Spring profile: local
# Or:
make local-run              # starts all 7 services via Gradle
```

### Option 3: Start UI (for either option above)

```bash
make ui-install             # npm ci (first time only)
make ui-dev                 # ng serve --open → opens http://localhost:4200
```

### Access URLs

| What                 | URL                                              |
|----------------------|--------------------------------------------------|
| **Angular UI**       | http://localhost:4200 (needs `make ui-dev`)       |
| **API Gateway**      | http://localhost:8080                              |
| **Swagger UI (all)** | http://localhost:8080/swagger-ui.html              |
| **Portal Links**     | http://localhost:8080/api/v1/portal/links          |
| **Kibana**           | http://localhost:5601                              |
| **Elasticsearch**    | http://localhost:9200                              |
| **Prometheus**       | http://localhost:8080/actuator/prometheus           |

**Default login:** `admin` / `admin123`

## Make Commands

### Build & Test
| Command          | Description                                      |
|------------------|--------------------------------------------------|
| `make clean`     | Gradle clean                                     |
| `make build`     | Gradle build (skip tests)                        |
| `make test`      | Run unit tests (skip e2e)                        |

### Docker — Full Stack
| Command            | Description                                                |
|--------------------|------------------------------------------------------------|
| `make all`         | **Full pipeline:** check-ports → clean → build → docker-build → docker-up |
| `make docker-build`| Build all Docker images                                    |
| `make docker-up`   | Start the full cluster (17 containers)                     |
| `make docker-down` | Stop and remove all containers                             |
| `make docker-logs` | Tail logs from all containers                              |
| `make docker-prune`| **Nuclear:** stop containers + delete `.docker-data/` + remove images |
| `make status`      | Show container status + disk usage of local volumes        |
| `make check-ports` | Pre-flight check — fails if containers already running     |


### Infrastructure Only (IntelliJ dev)
| Command          | Description                                        |
|------------------|----------------------------------------------------|
| `make infra-up`  | Start PG ×3 + Redis (for IntelliJ local dev)       |
| `make infra-down`| Stop infrastructure containers                     |
| `make local-run` | Start infra + build + boot all 7 services locally  |
| `make local-stop`| Kill all Gradle bootRun processes                  |

### Angular UI
| Command          | Description                                        |
|------------------|----------------------------------------------------|
| `make ui-install`| `npm ci` inside banking-ui/                        |
| `make ui-dev`    | `ng serve --open` → http://localhost:4200           |
| `make ui-build`  | Production build                                   |
| `make ui-test`   | Run Angular unit tests (headless Chrome)           |

### E2E Tests
| Command          | Description                                        |
|------------------|----------------------------------------------------|
| `make e2e`       | Run E2E tests against already-running cluster      |
| `make e2e-full`  | docker-up → wait 60s → run E2E tests              |

### OpenAPI / Swagger
| Command              | Description                                                 |
|----------------------|-------------------------------------------------------------|
| `make swagger-export`| Export JSON + YAML specs from running cluster → `docs/swagger/` |

> **Swagger UI:** http://localhost:8080/swagger-ui.html — dropdown at top-right lets you switch between Orchestrator, MDM, CCSL, and Auth.

### Helm (Kubernetes)
| Command             | Description                                     |
|---------------------|-------------------------------------------------|
| `make helm-install` | Install Helm chart                              |
| `make helm-uninstall`| Uninstall Helm chart                           |
| `make helm-upgrade` | Upgrade Helm release                            |

### Shortcuts
| Command       | Description                     |
|---------------|---------------------------------|
| `make kibana` | Open Kibana in browser          |

## Angular UI

The Angular UI is a **client-side SPA** — it runs entirely in your **browser**, not on a server. Run it alongside the Docker cluster:

```bash
make ui-install             # first time only
make ui-dev                 # ng serve → http://localhost:4200
```

The UI on `:4200` calls the gateway on `:8080`, which is CORS-configured to allow it.

## API Endpoints

### Search (via Gateway :8080)
- `POST /api/v1/search/phone` — `{"phoneNumber": "4161234567"}`
- `POST /api/v1/search/name` — `{"fullName": "John Doe"}`
- `POST /api/v1/search/email` — `{"email": "john.doe@example.com"}`

### Accounts
- `GET /api/v1/accounts/{accountNumber}/cards` — all cards
- `GET /api/v1/accounts/{accountNumber}/cards/credit` — credit cards only
- `GET /api/v1/accounts/{accountNumber}/cards/debit` — debit cards only
- `GET /api/v1/accounts/{accountNumber}/summary` — full summary

### Cards
- `POST /api/v1/cards/validate` — `{"cardNumber": "4111111111111111"}`

### Auth
- `POST /auth/register` — `{"username","password","email","fullName"}`
- `POST /auth/login` — `{"username","password"}`
- `GET /auth/me` — current user info (JWT required)
- `GET /auth/users` — list all (JWT required)
- `PUT /auth/users/{id}/role` — `{"role": "ADMIN"}` (ADMIN only)

### Health
- `GET /api/v1/health/downstream` — check MDM + CCSL status (JWT required)

## OpenAPI / Swagger

All service docs are accessible from a **single Swagger UI** at the gateway (no CORS issues):

**→ http://localhost:8080/swagger-ui.html**

Use the dropdown at the top to switch between services:
- **Gateway** — gateway routes
- **Orchestrator** — search, accounts, cards, health
- **MDM** — account data
- **CCSL** — card tokenization
- **Auth** — register, login, user management

## Kibana — How to Navigate

Open **http://localhost:5601** in your browser.

### First-Time Setup (create index pattern)

1. Open Kibana → click **☰ hamburger menu** (top-left)
2. Go to **Management** → **Stack Management**
3. Under "Kibana", click **Data Views** (or "Index Patterns" in older versions)
4. Click **Create data view**
5. Enter name: `banking-logs` and index pattern: `banking-logs-*`
6. Select `@timestamp` as the time field
7. Click **Save data view to Kibana**

### View Logs

1. Click **☰** → **Analytics** → **Discover**
2. Select the `banking-logs` data view from the dropdown
3. Set the time range (top-right) to **Last 15 minutes** or **Today**
4. You'll see structured JSON logs from all services

### Useful Filters

Click **+ Add filter** to narrow down:

| Filter by          | Field          | Example value          |
|--------------------|----------------|------------------------|
| Service name       | `service`      | `orchestrator-service` |
| Correlation ID     | `correlationId`| (paste from a response header) |
| Log level          | `level`        | `ERROR`                |
| Logger             | `logger_name`  | `com.banking.platform` |

### Trace a Request End-to-End

1. Make an API call and note the `X-Correlation-Id` response header
2. In Kibana Discover, filter: `correlationId: <your-id>`
3. You'll see the request flow across all services in chronological order

### Create a Dashboard

1. **☰** → **Analytics** → **Dashboard** → **Create dashboard**
2. Click **Create visualization**
3. Examples:
   - **Bar chart**: Log count by `service` over time
   - **Pie chart**: Distribution by `level` (INFO/WARN/ERROR)
   - **Metric**: Total ERROR count in last hour
4. Save the dashboard

> **Note:** Logs only flow to Kibana when running in Docker (`docker` profile enables the Logstash appender). Local IntelliJ dev logs to console only.

## Test Users

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user1    | admin123 | USER  |
| manager  | admin123 | ADMIN |

## Data Volumes

Docker data is stored locally in `.docker-data/` (gitignored):
```
.docker-data/
  pgdata-mdm/      # MDM PostgreSQL data
  pgdata-ccsl/     # CCSL PostgreSQL data
  pgdata-auth/     # Auth PostgreSQL data
  esdata/          # Elasticsearch indices
```

To wipe all data and start fresh: `make docker-prune`

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.4, Spring Cloud Gateway MVC, Spring Data JPA, Flyway, Micrometer
- **Frontend**: Angular 17, Angular Material
- **Security**: JWT (HMAC-SHA384), gateway-level + service-level validation
- **Data**: PostgreSQL 15, Redis 7
- **Observability**: ELK Stack (Elasticsearch, Logstash, Kibana), Prometheus metrics
- **Build**: Gradle 9.x (multi-module), Angular CLI
- **Infra**: Docker Compose, Helm, GitHub Actions

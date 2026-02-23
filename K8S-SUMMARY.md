# Kubernetes Deployment Summary

## Cluster Status

All 12 pods running on Docker Desktop Kubernetes (Kubeadm v1.34.1, single-node):

| Pod | Role |
|-----|------|
| `banking-ui` | Angular SPA served via nginx |
| `gateway-service` | Spring Cloud Gateway MVC |
| `orchestrator-service` | Step pipeline + virtual threads |
| `mdm-service` | Account master data (PostgreSQL) |
| `ccsl-service` | Card tokenization (PostgreSQL) |
| `orchestrator-auth` | User management + JWT (PostgreSQL) |
| `mdm-auth` | Service-to-service auth |
| `ccsl-auth` | Service-to-service auth |
| `postgres-mdm` | PostgreSQL for MDM |
| `postgres-ccsl` | PostgreSQL for CCSL |
| `postgres-auth` | PostgreSQL for Auth |
| `redis` | Token cache for orchestrator |

## Access URLs (Fixed Ports)

| Service | URL | Port Type |
|---------|-----|-----------|
| 🌐 **Angular UI** | http://localhost:30042 | NodePort (fixed) |
| 🚪 **API Gateway** | http://localhost:30080 | NodePort (fixed) |
| 📖 **Swagger UI** | http://localhost:30080/swagger-ui.html | via Gateway |
| 💚 **Health Check** | http://localhost:30080/actuator/health | via Gateway |

> **Ports are fixed** — they are hardcoded as `nodePort` values in the Helm templates and will always be `30042` (UI) and `30080` (Gateway) on every deployment.

## Docker Hub Images

All images pushed to `docker.io/nishantmalhotra1/`:

- `banking-platform-api-gateway:latest`
- `banking-platform-orchestrator-service:latest`
- `banking-platform-mdm-service:latest`
- `banking-platform-ccsl-service:latest`
- `banking-platform-orchestrator-auth:latest`
- `banking-platform-mdm-auth:latest`
- `banking-platform-ccsl-auth:latest`
- `banking-platform-ui:latest`

## Make Commands

| Command | Description |
|---------|-------------|
| `make helm-install` | Install Helm chart on K8s |
| `make helm-uninstall` | Uninstall + delete namespace |
| `make helm-upgrade` | Upgrade existing release |
| `make helm-deploy` | Full: build → push → uninstall → install |
| `make helm-status` | Show pods, services, helm release |
| `make docker-push` | Tag + push all images to Docker Hub |

## Architecture (Kubernetes)

```
Browser
  │
  ├──► http://localhost:30042  →  [banking-ui nginx pod]
  │                                    │ /api/* proxy
  │                                    ▼
  └──► http://localhost:30080  →  [gateway-service pod]
                                       │
                    ┌──────────────────┼──────────────────┐
                    ▼                  ▼                  ▼
          [orchestrator-service]  [orchestrator-auth]     │
                    │                  │                  │
              ┌─────┴─────┐      [postgres-auth]         │
              ▼           ▼                              │
        [mdm-service] [ccsl-service]                     │
              │           │                              │
        [postgres-mdm] [postgres-ccsl]             [redis]
```


.PHONY: clean build test docker-build docker-up docker-down docker-logs docker-prune \
       infra-up infra-down local-run local-stop \
       ui-install ui-dev ui-build ui-test \
       docker-push helm-install helm-uninstall helm-upgrade helm-deploy helm-status \
       e2e e2e-full all kibana check-ports status swagger-export

# Project name used by docker compose (auto-derived from folder, but we pin it)
COMPOSE_PROJECT := banking-platform

# ---------------------------------------------------------------
# Java Build
# ---------------------------------------------------------------

clean:
	./gradlew clean

build:
	./gradlew build -x test

test:
	./gradlew test -x :e2e-tests:test

# ---------------------------------------------------------------
# Pre-flight: detect if our containers are already running
# ---------------------------------------------------------------

check-ports:
	@echo "🔍 Checking for existing banking-platform containers..."
	@RUNNING=$$(docker compose ps -q 2>/dev/null | wc -l | tr -d ' '); \
	if [ "$$RUNNING" -gt "0" ]; then \
		echo ""; \
		echo "⚠️  Banking-platform containers are already running!"; \
		echo ""; \
		docker compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}"; \
		echo ""; \
		echo "👉 Please run 'make docker-down' first, then retry."; \
		echo "   Or run 'make docker-prune' to remove everything (including data)."; \
		echo ""; \
		exit 1; \
	fi; \
	echo "✅ No existing containers found — safe to proceed."

# ---------------------------------------------------------------
# Docker — Full Stack
# ---------------------------------------------------------------

docker-build:
	docker compose build

docker-up:
	docker compose up -d
	@echo ""
	@echo "✅ Banking Platform is running (Docker)!"
	@echo ""
	@echo "   🌐 UI:              http://localhost:4200"
	@echo "   🚪 Gateway:         http://localhost:8080"
	@echo "   📋 Portal Links:    http://localhost:8080/api/v1/portal/links"
	@echo "   📖 Swagger (all):   http://localhost:8080/swagger-ui.html"
	@echo "   📊 Kibana:          http://localhost:5601"
	@echo "   🔍 Elasticsearch:   http://localhost:9200"
	@echo ""
	@echo "   Default login:  admin / admin123"
	@echo ""

docker-down:
	@echo "🛑 Stopping all banking-platform containers..."
	@docker compose down 2>/dev/null || true
	@docker compose -f docker-compose-infra.yaml down 2>/dev/null || true
	@echo "✅ All containers stopped and removed."

docker-logs:
	docker compose logs -f

docker-prune:
	@echo "🧹 Pruning ALL banking-platform Docker resources..."
	@docker compose down -v --remove-orphans 2>/dev/null || true
	@docker compose -f docker-compose-infra.yaml down -v --remove-orphans 2>/dev/null || true
	@echo "🗑️  Removing local volume data (.docker-data/)..."
	@rm -rf .docker-data
	@echo "🗑️  Removing dangling project images..."
	@docker images --filter "reference=banking-platform-*" -q 2>/dev/null | xargs docker rmi -f 2>/dev/null || true
	@echo "✅ All banking-platform Docker resources cleaned."

status:
	@echo "📦 Container Status:"
	@docker compose ps --format "table {{.Name}}\t{{.Status}}\t{{.Ports}}" 2>/dev/null || echo "  (no containers running)"
	@echo ""
	@echo "💾 Local volume data:"
	@du -sh .docker-data/*/ 2>/dev/null || echo "  (no data — .docker-data/ does not exist)"

# ---------------------------------------------------------------
# Infrastructure Only (for IntelliJ local dev)
# ---------------------------------------------------------------

infra-up:
	docker compose -f docker-compose-infra.yaml up -d
	@echo ""
	@echo "✅ Infrastructure running (PG ×3, Redis)"
	@echo "   PostgreSQL MDM:   localhost:5433"
	@echo "   PostgreSQL CCSL:  localhost:5434"
	@echo "   PostgreSQL Auth:  localhost:5435"
	@echo "   Redis:            localhost:6379"
	@echo ""
	@echo "Now run services in IntelliJ with profile=local"
	@echo "  or use: make local-run"
	@echo ""

infra-down:
	@docker compose -f docker-compose-infra.yaml down 2>/dev/null || true
	@echo "✅ Infrastructure stopped."

# ---------------------------------------------------------------
# Local Run (all services via Gradle, profile=local)
# ---------------------------------------------------------------

local-run: infra-up build
	@echo "Starting all services locally with profile=local ..."
	@SPRING_PROFILES_ACTIVE=local ./gradlew :ccsl-auth:bootRun &
	@SPRING_PROFILES_ACTIVE=local ./gradlew :mdm-auth:bootRun &
	@sleep 3
	@SPRING_PROFILES_ACTIVE=local ./gradlew :ccsl-service:bootRun &
	@SPRING_PROFILES_ACTIVE=local ./gradlew :mdm-service:bootRun &
	@SPRING_PROFILES_ACTIVE=local ./gradlew :orchestrator-auth:bootRun &
	@sleep 5
	@SPRING_PROFILES_ACTIVE=local ./gradlew :orchestrator-service:bootRun &
	@sleep 3
	@SPRING_PROFILES_ACTIVE=local ./gradlew :api-gateway:bootRun &
	@echo ""
	@echo "✅ All services starting with profile=local"
	@echo "   Gateway:  http://localhost:8080"
	@echo "   Swagger:  http://localhost:8080/swagger-ui.html"
	@echo ""

local-stop:
	@echo "Stopping all Gradle bootRun processes..."
	@pkill -f "gradlew.*bootRun" || true
	@pkill -f "spring-boot:run" || true
	@echo "✅ Stopped"

# ---------------------------------------------------------------
# Angular UI
# ---------------------------------------------------------------

ui-install:
	cd banking-ui && npm ci

ui-dev:
	cd banking-ui && npx ng serve --open

ui-build:
	cd banking-ui && npx ng build

ui-test:
	cd banking-ui && npx ng test --watch=false --browsers=ChromeHeadless

# ---------------------------------------------------------------
# Helm
# ---------------------------------------------------------------

DOCKER_USER := nishantmalhotra1

docker-push: docker-build
	@echo "🏷️  Tagging and pushing images to Docker Hub ($(DOCKER_USER))..."
	@for svc in api-gateway orchestrator-service mdm-service ccsl-service orchestrator-auth mdm-auth ccsl-auth; do \
		docker tag "banking-platform-$${svc}:latest" "$(DOCKER_USER)/banking-platform-$${svc}:latest"; \
		docker push "$(DOCKER_USER)/banking-platform-$${svc}:latest"; \
	done
	@docker build -f banking-ui/Dockerfile -t $(DOCKER_USER)/banking-platform-ui:latest .
	@docker push $(DOCKER_USER)/banking-platform-ui:latest
	@echo "✅ All images pushed to docker.io/$(DOCKER_USER)"

helm-install:
	helm install banking-platform ./helm/banking-platform

helm-uninstall:
	helm uninstall banking-platform
	kubectl delete namespace banking-platform --ignore-not-found

helm-upgrade:
	helm upgrade banking-platform ./helm/banking-platform

helm-deploy: docker-push helm-uninstall
	@echo "⏳ Waiting for namespace cleanup..."
	@sleep 10
	helm install banking-platform ./helm/banking-platform
	@echo ""
	@echo "✅ Banking Platform deployed on Kubernetes!"
	@echo ""
	@echo "   🌐 UI:       http://localhost:30042"
	@echo "   🚪 Gateway:  http://localhost:30080"
	@echo "   📖 Swagger:  http://localhost:30080/swagger-ui.html"
	@echo ""

helm-status:
	@echo "📦 Helm Release:"
	@helm list -A
	@echo ""
	@echo "📦 Pods:"
	@kubectl get pods -n banking-platform
	@echo ""
	@echo "📦 Services:"
	@kubectl get svc -n banking-platform

# ---------------------------------------------------------------
# E2E Tests (requires cluster running)
# ---------------------------------------------------------------

e2e:
	@echo "Running E2E tests against running cluster..."
	./gradlew :e2e-tests:test --info

e2e-full: docker-up
	@echo "Waiting 60s for cluster to stabilize..."
	@sleep 60
	./gradlew :e2e-tests:test --info || true
	@echo "E2E tests complete. Run 'make docker-down' to stop."


# ---------------------------------------------------------------
# Swagger / OpenAPI Export
# ---------------------------------------------------------------

swagger-export:
	@./scripts/export-swagger.sh

# ---------------------------------------------------------------
# Shortcuts
# ---------------------------------------------------------------

all: check-ports clean build docker-build docker-up

kibana:
	open http://localhost:5601


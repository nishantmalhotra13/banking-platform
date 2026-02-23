#!/usr/bin/env bash
# Export OpenAPI docs from all running services as JSON and YAML
# Requires: the cluster running (make all / make docker-up), python3, and pip install pyyaml
#
# Usage: ./scripts/export-swagger.sh
# Output: docs/swagger/<service>.json and docs/swagger/<service>.yaml

set -e

GATEWAY="http://localhost:8080"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUTDIR="${SCRIPT_DIR}/../docs/swagger"
mkdir -p "$OUTDIR"

# Check if python3 and pyyaml are available
if ! python3 -c "import yaml" 2>/dev/null; then
    echo "⚠️  pyyaml not installed. JSON will be exported but YAML conversion skipped."
    echo "   Install with: pip3 install pyyaml"
    YAML_AVAILABLE=false
else
    YAML_AVAILABLE=true
fi

SERVICES="orchestrator mdm ccsl auth"
ENDPOINTS="/services/orchestrator/v3/api-docs /services/mdm/v3/api-docs /services/ccsl/v3/api-docs /services/auth/v3/api-docs"

echo "📖 Exporting OpenAPI docs from running cluster..."
echo ""

i=1
for svc in $SERVICES; do
    endpoint=$(echo "$ENDPOINTS" | cut -d' ' -f$i)
    json_file="$OUTDIR/${svc}.json"
    yaml_file="$OUTDIR/${svc}.yaml"

    printf "  %-15s ... " "$svc"

    HTTP_CODE=$(curl -s -o "$json_file" -w "%{http_code}" "${GATEWAY}${endpoint}")

    if [ "$HTTP_CODE" = "200" ]; then
        # Pretty-print the JSON
        python3 -m json.tool "$json_file" > "${json_file}.tmp" && mv "${json_file}.tmp" "$json_file"

        if [ "$YAML_AVAILABLE" = true ]; then
            python3 -c "
import json, yaml
with open('$json_file') as f:
    data = json.load(f)
with open('$yaml_file', 'w') as f:
    yaml.dump(data, f, default_flow_style=False, sort_keys=False, allow_unicode=True)
"
            echo "✅ JSON + YAML"
        else
            echo "✅ JSON only"
        fi
    else
        echo "❌ HTTP $HTTP_CODE"
        rm -f "$json_file"
    fi

    i=$((i + 1))
done

echo ""
echo "📁 Files exported to: $OUTDIR/"
ls -la "$OUTDIR/"


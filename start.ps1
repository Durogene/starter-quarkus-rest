
# Se placer dans le dossier du projet
Set-Location $PSScriptRoot

# Configuration PostgreSQL
$env:DB_HOST = "localhost"
$env:DB_PORT = "15432"
$env:DB_NAME = "products"
$env:DB_USER = "tpuser"
$env:DB_PASSWORD = "Tp@2026"

# Configuration Java pour privilegier IPv4
$env:JAVA_TOOL_OPTIONS = "-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false -Dfile.encoding=UTF-8"
# Demander le token si absent
if (-not $env:DISCORD_TOKEN) {
    $secureToken = Read-Host "Token Discord" -AsSecureString
    $env:DISCORD_TOKEN = [System.Net.NetworkCredential]::new(
        "", $secureToken
    ).Password
}

# Demarrer PostgreSQL
docker compose up -d postgres

if ($LASTEXITCODE -ne 0) {
    Write-Error "Impossible de demarrer PostgreSQL"
    exit 1
}

# Demarrer Quarkus
.\mvnw.cmd quarkus:dev

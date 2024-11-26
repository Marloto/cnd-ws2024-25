# Docker Experimente

## Vorbereitung

Zur Vorbereitung empfiehlt es sich die folgenen Images bereits zu laden.

```bash
docker pull eclipse-temurin:17-jre-alpine
docker pull eclipse-temurin:17-jdk-alpine
docker pull redis:alpine
docker pull mongo:latest
docker pull node:18-alpine
docker pull python:3.11-alpine
```

## Aufgabe 1

Erstellen Sie ein Dockerfile für eine der vorgegebenen Anwendungen (Node.js/Express, Python/FastAPI, Java/Spring) mit folgenden Anforderungen:

- Passendes Basis-Image
- Installation der notwendigen Dependencies (z.B. mittels `npm i`, sobald eine `package.json` im selben Ordner liegt)
- Kopieren der Anwendungsdateien (z.B. `app.js`)
- Konfiguration des Startbefehls (z.B. eine Node-Anwendung kann mittels `npm run start` gestartet werden)

Testen Sie das Dockerfile durch:

- Bauen des Images
- Starten eines Containers
- Überprüfen der Erreichbarkeit der Basis-Route (`/`, `/hello` oder `/health`), Fehler bzgl. Datenbanken sind erwartbar

## Aufgabe 2

Erweitern Sie das Setup um ein docker-compose.yml mit:

- Ihrer ausgewählte Anwendung
- Redis Service (recherchieren Sie hierzu im Docker Hub)
- MongoDB Service (recherchieren Sie hierzu im Docker Hub)
- Korrekte Netzwerkkonfiguration zwischen den Services (vgl. genutzte Umgebungsvariablen in app.js, app.py bzw. application.properties)
- Volume für MongoDB Persistenz

Testen Sie die Compose-Umgebung durch:

- Starten der Services
- Test der Redis-Funktionalität (/counter)
- Test der MongoDB-Funktionalität (/log)
- Überprüfen der Persistenz nach Neustart, räume Sie auch die komplette Anwendung mittels docker-compose down weg und erzeugen Sie neu - ist der Zustand noch da, welcher fehlt?

## Aufgabe 3

Erweitern Sie Ihre Docker Compose Konfiguration um:

1. Implementieren Sie Restart-Policies:
   - Konfigurieren Sie passende restart-Optionen für alle Services
   - Begründen Sie Ihre Wahl (always, unless-stopped, on-failure)

2. Fügen Sie Health Checks hinzu:
   - Endpoint für die Anwendungen ist (vgl. `/health`)
   - Redis Verbindungstest
   - MongoDB Verfügbarkeit

3. Definieren Sie Resource Limits:
   - CPU und Memory Limits für die Anwendung
   - Monitoring der tatsächlichen Nutzung

Testen Sie Ihre Konfiguration:
- Simulieren Sie Ausfälle und beobachten das Restart-Verhalten
- Testen Sie das Verhalten bei Ressourcenknappheit
- Validieren Sie die Health Checks

## Aufgabe 4

Migrieren Sie Ihre Anwendung zu Docker Swarm, nutzen Sie Kapitel 5 im Skript als Einstieg für Befehle wie:

```
docker swarm init
docker stack deploy --compose-file=docker-compose.yml my-deployment-name
docker service ls
docker service update --force my-deployment-name_app
```

1. Erweitern Sie die Compose-Datei um Swarm-spezifische Funktionen:
   - Replizierung der App (3 Instanzen)
   - Update-Strategien (Rolling Updates)
   - Placement Constraints für Datenbanken

2. Konfigurieren Sie die Swarm-Umgebung:
   - Swarm Initialisierung
   - Deployment als Stack

3. Testen Sie die Swarm-Funktionalitäten:
   - Load Balancing zwischen Instanzen, nutzen Sie `docker log -f <name>` bzw. die Antworten um zu sehen, welcher Knoten antwortet
   - Verhalten bei Node-Ausfällen
   - Rolling Updates
   - Skalierung zur Laufzeit

4. Führen Sie ein Update durch
    - Ändern Sie etwas an der Anwendung (z.B. für `/hello` die Message auf "Hello, Universe")
    - führen Sie ein Update durch und beobachten Sie den Prozess

Dokumentieren Sie die Unterschiede:
- Vergleich der Konfigurationsoptionen
- Vor- und Nachteile gegenüber single-host Compose
- Beobachtungen zum Systemverhalten
- Beobachtungen zum Update

## Fragen zur Auswertung

- Container Basics:
    1. Welches Base Image haben Sie gewählt und warum?
    1. Wie wurde die Reihenfolge der Dockerfile-Anweisungen gewählt und warum ist diese wichtig?
    1. Was befindet sich am Ende im finalen Image?
- Netzwerk & Kommunikation:
    1. Wie kommunizieren die Container untereinander?
    1. Wie wurde die Verbindung zu Redis und MongoDB konfiguriert?
    1. Was passiert, wenn einer der abhängigen Services (Redis/MongoDB) nicht erreichbar ist?
- Datenpersistenz:
    1. Welche Daten bleiben nach einem docker-compose down erhalten?
    1. Welche Daten gehen verloren und warum?
- Health & Restart:
    1. Wie verhält sich das System beim Ausfall eines Services?
    1. Welche Restart-Policy haben Sie gewählt und warum?
    1. Was macht der Health Check und wie wird er genutzt?
- Last & Ressourcen:
    1. Was passiert bei CPU-Stress (/stress/cpu)?
    1. Wie wirken sich die gesetzten Resource Limits aus?
- Swarm Spezifisch:
    1. Wie erkennen Sie, welche Instanz eine Anfrage bearbeitet?
    1. Was passiert während eines Rolling Updates?
    1. Wie verhält sich der Load Balancer bei mehreren Instanzen?
    1. Was sind die wichtigsten Unterschiede zwischen Compose und Swarm Setup?
- Praktische Erfahrungen:
    1. Welche Probleme sind aufgetreten?
    1. Wie wurden diese gelöst?
    1. Was würden Sie beim nächsten Mal anders machen?
    1. Welche Vorteile bietet die Container-Orchestrierung in Ihrem Setup?
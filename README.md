# network-testing-system

A system for automated network performance testing and monitoring. It runs scheduled measurements (ping, iperf3 TCP/UDP) against a set of configured hosts, stores the results in a database and shows them over time in Grafana.

## Why

Checking network performance by hand with iperf3 or ping is repetitive and leaves no history. It is hard to compare results across days, hosts or network paths. The goal is to automate the measurements, keep the results and make changes in performance visible.

## How it works

The system has three parts that talk to each other over REST:

- **Backend** (Java, Spring Boot) manages targets and test plans, keeps a queue of jobs and stores measurement results.
- **Runner** (Python) polls the backend for jobs, runs the actual test (ping or iperf3), and posts the result back as JSON.
- **Grafana** reads the measurements straight from the database and draws the dashboards.

The runner pulls work from the backend instead of being called by it. This keeps the backend independent of where the runner lives, and jobs simply wait in the queue if the runner is down.

```
Swagger UI --> Backend (Spring Boot) <--> Runner (Python) --> iperf3 / ping targets
                    |
               PostgreSQL <-- Grafana
```

Measured values: throughput, retransmissions (TCP), jitter and packet loss (UDP), round trip time.

## Tech stack

- Java 25, Spring Boot, Spring Data JPA, Lombok
- PostgreSQL
- Python 3
- iperf3
- Grafana
- Docker Compose
- springdoc-openapi (Swagger UI as the admin interface)

## Planned Project layout

```
backend/    Spring Boot application
runner/     Python measurement runner
deploy/     docker-compose and Grafana configuration
docs/       notes and diagrams
```

## Running it
Start the app in root folder with:

```
cd backend
./mvnw spring-boot:run
```

Thanks to the spring-boot-docker-compose dependency the containers will start as well.

Swagger UI is then available at `http://localhost:8080/swagger-ui.html`.

The runner and the Grafana setup will be documented here once they are in place.

## Status

Work in progress.

Done:
- project structure, PostgreSQL in Docker
- targets API (list of hosts to measure)

Planned:
- jobs and their lifecycle (pending, running, done, failed)
- Python runner with ping, then iperf3 TCP and UDP
- test plans with a scheduler
- safe job assignment with `FOR UPDATE SKIP LOCKED`, timeouts and retries
- Grafana dashboards
- Flyway migrations

Out of scope: multiple distributed probes, automatic device discovery, a custom web frontend.

## Author

Michal Rodak

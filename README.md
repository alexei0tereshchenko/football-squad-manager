# Football Squad Manager

Platform for amateur 5-a-side football teams: Telegram polls for match booking, balanced squad assignment, and player ratings — manual today, AI/video-assisted tomorrow.

Built as a **modular monolith** with a documented path to horizontal scaling: virtual sharding, idempotent vote handling, S3-compatible video storage, and k3d-based local deployment.

---

## Why this project?

Amateur football runs on chats and spreadsheets. A group of 20 friends plays twice a week — booking a pitch, splitting into balanced teams, and collecting feedback after the game is still manual. This project turns that routine into a small, reliable system.

It is also an engineering playground for patterns that matter at scale: **idempotency**, **sharding**, **event-driven module boundaries**, and **pluggable rating engines**.

---

## Features

- **Telegram-native booking** — create a match, publish a poll, collect votes.
- **Balanced squad assignment** — assign players to teams based on ratings or simple heuristics.
- **Player ratings** — start with manual Telegram polls; the `RatingEngine` interface is ready for AI/video-based scoring.
- **Data portability** — database dumps and S3-compatible video storage are first-class concerns, so the app can move between machines without data loss.
- **Scale-ready design** — virtual sharding by team, idempotent writes, and a modular monolith that can be decomposed later.

---

## Tech stack

| Layer | Choice |
|---|---|
| Language / Runtime | Java 21 (LTS) |
| Framework | Spring Boot 3.4.x |
| Modularity | Spring Modulith (package-by-module, architecture tests) |
| Database | PostgreSQL 16 (Flyway migrations) |
| Messaging (in-process) | Spring `ApplicationEventPublisher` → later RabbitMQ/Kafka |
| Object storage | S3-compatible API — RustFS locally, any S3 provider later |
| Testing | JUnit 5, Testcontainers (`@ServiceConnection`), REST Assured |
| CI/CD | GitHub Actions → GHCR → self-hosted runner → k3d |
| Local Kubernetes | k3d (k3s in Docker) |

---

## Architecture at a glance

```mermaid
flowchart LR
    TG([Telegram Platform])
    
    subgraph App [football-squad-manager - Spring Boot]
        direction TB
        T[telegram] --> M[match]
        M --> S[squad]
        S --> R[rating]
    end
    
    DB[(PostgreSQL\nSharded)]
    S3[(RustFS / S3\nVideo Storage)]

    TG -- "Webhook / Long Polling" --> App
    App -- "State" --> DB
    App -- "Video" --> S3

# Helios API

Helios is a real-time flight monitoring platform built with Java and Spring Boot. It consumes live aircraft data from the OpenSky Network REST API and exposes it to clients through Server-Sent Events (SSE), providing a continuous stream of flight updates.

Since the OpenSky API is REST-based, Helios uses a polling service to periodically retrieve the latest aircraft states. The application caches the most recent data in memory and broadcasts updates to all connected clients via SSE, avoiding unnecessary requests to the external API.

The project is designed with a modular architecture that separates data acquisition, event publishing, and client communication, making it easy to extend with additional data sources, persistence, analytics, and alerting capabilities.

## System Architecture

The following diagram illustrates the architecture of the Helios system:

![System Architecture](@docs/architecture/arch.png)

### Key Components

*   **Clients (Web/Mobile)**: Connect to `helios-api` to make HTTP requests and receive live updates through a Server-Sent Events (SSE) stream.
*   **Helios API (`helios-api`)**: Serves REST and SSE endpoints, publishes events, and leverages an in-memory cache.
*   **Cache**: An in-memory cache system inside `helios-api` used to temporarily store and quickly retrieve flight data.
*   **Scheduler (Polling Service)**: A scheduling component that periodically polls the external OpenSky API for fresh flight data and pushes updates to the `helios-api`.
*   **OpenSky API**: External service that provides live flight data.

## Features

*   Consume real-time aircraft data from the OpenSky Network
*   Stream live flight updates using Server-Sent Events (SSE)
*   In-memory caching for efficient data distribution
*   Polling-based integration with external REST APIs
*   Modular and extensible architecture
*   OpenAPI/Swagger documentation
*   Dockerized deployment

## Tech Stack

*   Java 26 (configured in [pom.xml](file:///home/laura/git/helios-api/pom.xml))
*   Spring Boot
*   Spring Web
*   Server-Sent Events (SSE)
*   Docker
*   Maven
*   OpenSky Network API

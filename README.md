# US Locations API

A Spring Boot REST API for querying and managing US geographic data — states, counties, places, and climate statistics.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.7 |
| ORM | Spring Data JPA (Hibernate) |
| Database | PostgreSQL |
| Migrations | Flyway |
| Boilerplate reduction | Lombok |
| API Documentation | SpringDoc OpenAPI (Swagger UI) |

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL running locally on port `5432`

### Database Setup

Create the database before starting the app:

```sql
CREATE DATABASE us_geo;
```

Flyway will automatically run all migrations under `src/main/resources/db/migration` on startup.

### Running the App

```bash
./mvnw spring-boot:run
```

The application starts on **http://localhost:8080**.

---

## API Documentation (Swagger UI)

Once the app is running, the full interactive API documentation is available at:

| URL | Description |
|---|---|
| http://localhost:8080/swagger-ui.html | Interactive Swagger UI |
| http://localhost:8080/v3/api-docs | Raw OpenAPI JSON spec |

---

## API Endpoints

### States — `/api/states`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/states` | List states with optional search (`?q=`) and pagination (`?limit=&offset=`) |
| GET | `/api/states/all-states` | List all states (no pagination) |
| GET | `/api/states/{id}` | Get a state by database ID |
| GET | `/api/states/code/{code}` | Get a state by 2-letter code, e.g. `NY` |
| GET | `/api/states/capital/{capital}` | Get a state by its capital city name |
| POST | `/api/states` | Create a new state |
| PUT | `/api/states/{id}` | Update an existing state |
| DELETE | `/api/states/{id}` | Delete a state |

### Counties — `/api/counties`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/counties` | List counties with optional search and state filter (`?stateId=`) |
| GET | `/api/counties/all-counties` | List all counties (no pagination) |
| GET | `/api/counties/{id}` | Get a county by database ID |
| GET | `/api/counties/fips/{countyFips}` | Get a county by 5-character FIPS code, e.g. `36001` |
| POST | `/api/counties` | Create a new county |
| PUT | `/api/counties/{id}` | Update an existing county |
| DELETE | `/api/counties/{id}` | Delete a county |

### Places — `/api/places`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/places` | List places with optional search, state, county, and type filters |
| GET | `/api/places/all-places` | List all places (no pagination) |
| GET | `/api/places/{id}` | Get a place by database ID |
| GET | `/api/places/fips/{placeFips}` | Get a place by 7-character FIPS code, e.g. `3601000` |
| POST | `/api/places` | Create a new place |
| PUT | `/api/places/{id}` | Update an existing place |
| DELETE | `/api/places/{id}` | Delete a place |

### Climate Stats — `/api/climate-stats`

> Coming soon. The entity, repository, and mapper layers are in place.

---

## Place Types

The `placeType` field on places accepts the following values:

| Value | Description |
|---|---|
| `CITY` | Incorporated city |
| `TOWN` | Incorporated town |
| `VILLAGE` | Incorporated village |
| `CDP` | Census-Designated Place |
| `BOROUGH` | Borough |
| `OTHER` | Other place type |

---

## Database Schema

```
time_zones          — IANA time zone identifiers
states              — US states (code, FIPS, capital, area, population)
state_time_zones    — Many-to-many: states ↔ time zones
counties            — Counties linked to states (FIPS, seat)
places              — Cities/towns/etc. linked to states and counties
state_population_stats  — Yearly population snapshots per state
place_population_stats  — Yearly population snapshots per place
state_climate_stats     — Monthly/yearly climate averages per state
place_climate_stats     — Monthly/yearly climate averages per place
```

---

## Project Structure

```
src/main/java/com/pm/uslocations/
├── config/
│   ├── OpenApiConfig.java        # Swagger/OpenAPI metadata
│   └── WebConfig.java            # CORS configuration
├── controller/                   # REST controllers
├── dto/
│   ├── request/                  # Incoming request bodies
│   └── response/                 # Outgoing response bodies
├── enums/                        # PlaceType, ClimateAggregate
├── mapper/                       # Entity ↔ DTO conversion
├── model/                        # JPA entities
├── repo/                         # Spring Data JPA repositories
├── service/                      # Service interfaces
└── serviceImpl/                  # Service implementations

src/main/resources/
├── application.yml               # App configuration
└── db/migration/                 # Flyway SQL migrations
    ├── V0__enable_citext.sql
    ├── V1__init.sql
    ├── V2__seed_min.sql
    └── ...
```

---

## Configuration

Key settings in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/us_geo
    username: postgres
    password: root

server:
  port: 8080

springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

---

## Branch: `dev-test`

This branch adds **Swagger/OpenAPI documentation** on top of the `dev` branch.

### Changes introduced

| File | Change |
|---|---|
| `pom.xml` | Added `springdoc-openapi-starter-webmvc-ui:2.7.0` dependency |
| `config/OpenApiConfig.java` | New — defines API title, description, version, contact, and server URL |
| `controller/StateController.java` | Added `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter` annotations |
| `controller/CountyController.java` | Added `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter` annotations |
| `controller/PlaceController.java` | Added `@Tag`, `@Operation`, `@ApiResponse`, `@Parameter` annotations |
| `controller/StateClimateStatController.java` | Added `@RestController`, `@RequestMapping`, `@Tag` placeholder |
| `application.yml` | Added `springdoc` config block for Swagger UI and API docs paths |

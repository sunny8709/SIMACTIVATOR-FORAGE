# SIM Card Activator Microservice

This microservice is responsible for activating Telstra SIM cards. It receives activation requests, communicates with the SIM Card Actuator service, and stores the activation records in a database.

## Features

- Receive SIM card activation requests with customer email and ICCID
- Forward activation requests to the SIM Card Actuator service
- Store activation records in a database
- Query activation records by ID

## Technical Stack

- Java 11
- Spring Boot 2.7.3
- Spring Data JPA
- H2 Database (in-memory)
- Cucumber for BDD testing

## API Endpoints

### Activate SIM Card

```
POST /api/simcards
```

Request body:
```json
{
  "iccid": "string",
  "customerEmail": "string"
}
```

Response body:
```json
{
  "iccid": "string",
  "customerEmail": "string",
  "active": boolean
}
```

### Query SIM Card

```
GET /api/simcards?simCardId={id}
```

Response body:
```json
{
  "iccid": "string",
  "customerEmail": "string",
  "active": boolean
}
```

## Database Schema

The microservice uses an H2 in-memory database with the following schema:

**SimCard Table**
- id: LONG (Primary Key, Auto-incremented)
- iccid: VARCHAR
- customerEmail: VARCHAR
- active: BOOLEAN

## Testing

The microservice includes Cucumber BDD tests for two scenarios:

1. **Successful SIM Card Activation**
   - Uses ICCID "1255789453849037777" which will successfully activate
   - Verifies that the activation is successful
   - Verifies that the database record shows the SIM card as active

2. **Failed SIM Card Activation**
   - Uses ICCID "8944500102198304826" which will fail to activate
   - Verifies that the activation fails
   - Verifies that the database record shows the SIM card as inactive

### Running Tests

To run the tests, you need to:

1. Start the SIM Card Actuator service (JAR file in the services folder)
2. Run the Cucumber tests using Maven:

```
mvn test
```

## Flow Diagram

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│                 │     │                 │     │                 │
│  REST Client    │────▶│  Microservice   │────▶│  SIM Card       │
│                 │     │                 │     │  Actuator       │
└─────────────────┘     └────────┬────────┘     └─────────────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │                 │
                        │  Database       │
                        │                 │
                        └─────────────────┘
```

## Important Notes

- The SIM Card Actuator service must be running on http://localhost:8444/actuate
- The ICCID "1255789453849037777" will always result in a successful activation
- The ICCID "8944500102198304826" will always result in a failed activation

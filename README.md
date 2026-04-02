# 📚 Planner API

A secure and structured backend API that allows authenticated users to manage their activities by creating and organizing subjects and tasks — including, but not limited to, academic use cases.

This project was designed to simulate a real-world backend system, focusing on authentication, data integrity, relational modeling, and clean architecture using modern Java ecosystem tools.

---

# 🚀 Features

* User authentication (JWT)
* Subject management with weekly schedules
* Task management (scheduled and unscheduled)
* Per-user data isolation
* Flyway-managed relational database schema
* Stateless security architecture

---

# 🔐 Authentication

All protected endpoints require a valid JWT token.

### Login

```
POST http://localhost:8080/auth/login
```

```json
{
  "email": "anamaria@gmail.com",
  "password": "password123"
}
```

Response:

```json
{
  "token": "<jwt-token>"
}
```

Use the token in all protected endpoints:

```
Authorization: Bearer <jwt-token>
```

---

### Register

```
POST http://localhost:8080/auth/register
```

```json
{
  "name": "Ana Maria",
  "email": "anamaria@gmail.com",
  "password": "password123"
}
```

---

# 📘 Subject Endpoints

## Create Subject

```
POST http://localhost:8080/subjects
Authorization: Bearer <token>
```

```json
{
  "title": "Banco de Dados",
  "professor": "Josué",
  "schedules": [
    {
      "weekDay": "WEDNESDAY",
      "period": "MORNING_2"
    },
    {
      "weekDay": "FRIDAY",
      "period": "MORNING_1"
    }
  ]
}
```

---

## Get All Subjects

```
GET http://localhost:8080/subjects
Authorization: Bearer <token>
```

---

## Get Subject by Title

```
GET http://localhost:8080/subjects?title=Banco de Dados
Authorization: Bearer <token>
```

---

## Update Subject

```
PUT http://localhost:8080/subjects/<subject-id>
Authorization: Bearer <token>
```

Example body:

```json
{
  "title": "Banco de Dados II",
  "professor": "Josué",
  "schedules": [
    {
      "weekDay": "MONDAY",
      "period": "MORNING_1"
    }
  ]
}
```

---

## Delete Subject

```
DELETE http://localhost:8080/subjects/<subject-id>
Authorization: Bearer <token>
```

---

# 🗓️ Task Endpoints

## Create Scheduled Task

```
POST http://localhost:8080/tasks
Authorization: Bearer <token>
```

```json
{
  "plannedDate": "2026-02-28",
  "dueDateTime": "2026-03-10T10:00:00",
  "startDateTime": "2026-02-28T09:00:00",
  "endDateTime": "2026-02-28T10:30:00",
  "description": "Study React",
  "status": "TODO"
}
```

---

## Create Unscheduled Task

```json
{
  "plannedDate": "2026-03-25",
  "dueDateTime": "2026-04-01T08:00:00",
  "description": "Study data structures",
  "status": "DOING"
}
```

---

## Get All Tasks

```
GET http://localhost:8080/tasks
Authorization: Bearer <token>
```

---

## Get Tasks by Planned Date

```
GET http://localhost:8080/tasks?plannedDate=2026-02-28
Authorization: Bearer <token>
```

---

## Update Task

```
PUT http://localhost:8080/tasks/<task-id>
Authorization: Bearer <token>
```

Example:

```json
{
  "description": "Study advanced data structures",
  "status": "DONE"
}
```

---

## Delete Task

```
DELETE http://localhost:8080/tasks/<task-id>
Authorization: Bearer <token>
```

---

# 🛠️ Tech Stack

* Java 21
* Spring Boot
* Spring Security (JWT)
* Spring Data JPA
* PostgreSQL
* Flyway
* Maven
* Docker

---

# 🧠 Architecture Highlights

* Stateless JWT authentication
* Custom `OncePerRequestFilter`
* UUID primary keys
* Foreign keys with cascade delete
* Enum-based domain modeling
* Normalized fields for uniqueness constraints
* Indexed date columns for performance
* Flyway-managed migrations

---

# 🐳 Running Locally

### 1️⃣ Start PostgreSQL

```bash
docker compose up -d
```

### 2️⃣ Configure secret

```
api.security.token.secret=your_secret_key
```

### 3️⃣ Run application

```bash
./mvnw spring-boot:run
```

---

# 🎯 Project Purpose

This project was built to strengthen backend engineering skills through:

* Secure authentication implementation
* Database modeling and constraints
* Clean service-layer architecture
* Real-world API design
* Migration versioning
* Stateless system design

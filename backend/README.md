# Event Ticket Platform

A comprehensive Spring Boot-based event ticket management system with QR code generation, ticket validation, and OAuth2 security integration.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security](#security)
- [Development](#development)
- [Deployment](#deployment)
- [Contributing](#contributing)

## 🎯 Overview

The Event Ticket Platform is a robust backend system designed to handle the complete lifecycle of event ticket management. It provides APIs for event creation, ticket purchasing, QR code generation, and ticket validation, with built-in security and user management.

### Key Capabilities

- **Event Management**: Create, update, and manage events with different statuses
- **Ticket Types**: Define multiple ticket types with pricing and capacity
- **QR Code Generation**: Automatic QR code generation for ticket validation
- **Ticket Validation**: Real-time ticket validation with multiple methods
- **User Management**: Role-based access control (Organizer, Staff, Attendee)
- **Security**: OAuth2 integration with Keycloak for authentication

## ✨ Features

### Core Features
- ✅ Event creation and management
- ✅ Ticket type configuration
- ✅ Ticket purchasing and management
- ✅ QR code generation for tickets
- ✅ Real-time ticket validation
- ✅ User role management
- ✅ Event status tracking
- ✅ Pagination support

### Security Features
- ✅ OAuth2 Resource Server integration
- ✅ JWT token validation
- ✅ Role-based access control
- ✅ Secure API endpoints
- ✅ User provisioning

### Technical Features
- ✅ RESTful API design
- ✅ Database persistence with JPA/Hibernate
- ✅ Input validation
- ✅ Exception handling
- ✅ Docker containerization
- ✅ PostgreSQL database support

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Controllers   │  │  Exception      │  │   Filters    │ │
│  │                 │  │  Handlers       │  │              │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                     Business Layer                          │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │    Services     │  │    Mappers      │  │    Utils     │ │
│  │                 │  │                 │  │              │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    Data Access Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Repositories  │  │    Entities     │  │    Enums     │ │
│  │                 │  │                 │  │              │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                      Database Layer                         │
│                    PostgreSQL Database                      │
└─────────────────────────────────────────────────────────────┘
```

### Package Structure

```
com.devtiro.tickets/
├── controllers/          # REST API endpoints
├── services/            # Business logic
├── repositories/        # Data access layer
├── domain/             # Domain models and DTOs
│   ├── entities/       # JPA entities
│   ├── dtos/          # Data Transfer Objects
│   └── enums/         # Enumerations
├── mappers/            # Object mapping
├── config/             # Configuration classes
├── exceptions/         # Custom exceptions
├── filters/            # Security filters
└── util/               # Utility classes
```

## 🛠️ Technology Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 3.5.0** - Application framework
- **Spring Security** - Security framework
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM framework
- **PostgreSQL** - Primary database
- **H2** - Test database

### Security
- **OAuth2 Resource Server** - Authentication
- **JWT** - Token-based authentication
- **Keycloak** - Identity and access management

### Utilities
- **MapStruct** - Object mapping
- **Lombok** - Code generation
- **ZXing** - QR code generation
- **Maven** - Build tool

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Adminer** - Database management interface

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**
- **PostgreSQL** (optional, if not using Docker)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd tickets
```

### 2. Start Infrastructure Services

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- Adminer (database management) on port 8888
- Keycloak (authentication server) on port 9090

### 3. Configure Keycloak

1. Access Keycloak admin console: http://localhost:9090
2. Login with admin/admin
3. Create a new realm called "event-ticket-platform"
4. Create client applications and users as needed

### 4. Build and Run the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 5. Verify Installation

- Application: http://localhost:8080
- Database Admin: http://localhost:8888
- Keycloak Admin: http://localhost:9090

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Authentication
All API endpoints require OAuth2 JWT tokens. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

### Event Management

#### Create Event
```http
POST /events
Content-Type: application/json

{
  "name": "Tech Conference 2024",
  "start": "2024-06-15T09:00:00",
  "end": "2024-06-15T17:00:00",
  "venue": "Convention Center",
  "salesStart": "2024-01-01T00:00:00",
  "salesEnd": "2024-06-10T23:59:59",
  "status": "DRAFT"
}
```

#### List Events
```http
GET /events?page=0&size=10
```

#### Get Event Details
```http
GET /events/{event_id}
```

#### Update Event
```http
PUT /events/{event_id}
Content-Type: application/json

{
  "name": "Updated Tech Conference 2024",
  "status": "PUBLISHED"
}
```

#### Delete Event
```http
DELETE /events/{event_id}
```

### Ticket Management

#### List User Tickets
```http
GET /tickets?page=0&size=10
```

#### Get Ticket Details
```http
GET /tickets/{ticket_id}
```

#### Get Ticket QR Code
```http
GET /tickets/{ticket_id}/qr-codes
```

### Ticket Validation

#### Validate Ticket
```http
POST /ticket-validations
Content-Type: application/json

{
  "id": "ticket-uuid",
  "method": "QR_CODE"
}
```

### Published Events (Public)

#### List Published Events
```http
GET /published-events
```

## 🗄️ Database Schema

### Core Entities

#### Event
- `id` (UUID) - Primary key
- `name` (String) - Event name
- `start` (DateTime) - Event start time
- `end` (DateTime) - Event end time
- `venue` (String) - Event venue
- `salesStart` (DateTime) - Ticket sales start
- `salesEnd` (DateTime) - Ticket sales end
- `status` (Enum) - Event status
- `organizer` (User) - Event organizer
- `attendees` (List<User>) - Event attendees
- `staff` (List<User>) - Event staff

#### Ticket
- `id` (UUID) - Primary key
- `status` (Enum) - Ticket status
- `ticketType` (TicketType) - Associated ticket type
- `purchaser` (User) - Ticket purchaser
- `validations` (List<TicketValidation>) - Validation history
- `qrCodes` (List<QrCode>) - Generated QR codes

#### User
- `id` (UUID) - Primary key
- `email` (String) - User email
- `firstName` (String) - First name
- `lastName` (String) - Last name
- `role` (Enum) - User role

#### TicketType
- `id` (UUID) - Primary key
- `name` (String) - Ticket type name
- `description` (String) - Description
- `price` (BigDecimal) - Ticket price
- `capacity` (Integer) - Available capacity
- `event` (Event) - Associated event

### Enums

#### EventStatusEnum
- `DRAFT` - Event is in draft mode
- `PUBLISHED` - Event is published and visible
- `CANCELED` - Event has been canceled
- `COMPLETED` - Event has finished

#### TicketStatusEnum
- `PURCHASED` - Ticket has been purchased
- `CANCELED` - Ticket has been canceled

## 🔐 Security

### Authentication Flow

1. **Client obtains JWT token** from Keycloak
2. **Token validation** by Spring Security OAuth2 Resource Server
3. **User provisioning** via custom filter
4. **Role-based authorization** for API endpoints

### Security Configuration

```java
@Configuration
public class SecurityConfig {
    // Configured endpoints:
    // - /api/v1/published-events/** (public)
    // - /api/v1/events (ORGANIZER role)
    // - /api/v1/ticket-validations (STAFF role)
    // - All other endpoints (authenticated)
}
```

### User Roles

- **ORGANIZER**: Can create, update, and manage events
- **STAFF**: Can validate tickets at events
- **ATTENDEE**: Can purchase and view tickets

## 🛠️ Development

### Project Structure

```
src/
├── main/
│   ├── java/com/devtiro/tickets/
│   │   ├── controllers/     # REST controllers
│   │   ├── services/        # Business logic
│   │   ├── repositories/    # Data access
│   │   ├── domain/          # Domain models
│   │   ├── mappers/         # Object mappers
│   │   ├── config/          # Configuration
│   │   ├── exceptions/      # Custom exceptions
│   │   ├── filters/         # Security filters
│   │   └── util/            # Utilities
│   └── resources/
│       └── application.properties
└── test/                    # Test classes
```

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Run with Spring Boot
mvn spring-boot:run
```

### Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EventServiceTest

# Run with coverage
mvn test jacoco:report
```

### Database Management

Access the database through Adminer:
- URL: http://localhost:8888
- System: PostgreSQL
- Server: db
- Username: postgres
- Password: changemeinprod!
- Database: postgres

## 🚀 Deployment

### Docker Deployment

1. **Build the application**
```bash
mvn clean package -DskipTests
```

2. **Create Docker image**
```bash
docker build -t event-ticket-platform .
```

3. **Run with Docker Compose**
```bash
docker-compose -f docker-compose.prod.yml up -d
```

### Environment Variables

Configure the following environment variables for production:

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/your-db
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password

# Security
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=https://your-keycloak-url/realms/event-ticket-platform

# Server
SERVER_PORT=8080
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding conventions
- Write unit tests for new features
- Update documentation for API changes
- Use meaningful commit messages
- Ensure all tests pass before submitting PR

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:

- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🔄 Version History

- **v1.0.0** - Initial release with core functionality
- **v1.1.0** - Added QR code generation and validation
- **v1.2.0** - Enhanced security with OAuth2 integration

---

**Note**: This is a development version. For production use, ensure proper security configurations and environment-specific settings. 

# POS Backend

A RESTful backend for a Point of Sale management system. The application manages master data, purchase inventory, user access, and password recovery workflows through a Spring Boot API.

## Features

- JWT-based authentication with BCrypt password hashing
- Role-based user administration (`ROLE_ADMIN` and `ROLE_USER`)
- Password recovery using email OTP verification
- Company, unit, product, vendor, and financial-year management
- Vendor-to-company associations
- Purchase invoices with line items, tax and discount fields, and transactional stock updates
- Pagination, request validation, soft deletion, and standardized API responses
- Swagger/OpenAPI documentation

## Technology Stack

- Java 21
- Spring Boot
- Spring Web, Spring Data JPA, Spring Security, and Bean Validation
- Hibernate and MySQL
- JWT (JJWT)
- Spring Mail
- Lombok
- Maven

## Architecture

The codebase follows a layered backend structure:

```text
controller  -> HTTP endpoints and response handling
service     -> business rules and transactions
repository  -> persistence access through Spring Data JPA
entity      -> database mappings
dto         -> request validation and response models
security    -> JWT authentication and authorization
```

## Getting Started

### Prerequisites

- JDK 21
- MySQL 8 or later
- Maven, or the Maven wrapper included in this repository

### Run locally

1. Create a MySQL database for the active profile.
2. Configure local database, JWT, and mail settings in `src/main/resources/application.properties` and the selected profile file.
3. Start the application:

```bash
sh mvnw spring-boot:run
```

4. Open the API documentation at:

```text
http://localhost:8080/swagger-ui/index.html
```

Do not commit real database passwords, JWT signing keys, or email credentials. Use environment variables or a secret manager for deployment.

## API Overview

| Area | Base path |
| --- | --- |
| Authentication and password recovery | `/api/auth` |
| Application users | `/api/users` |
| Companies | `/api/companies` |
| Units | `/api/units` |
| Products | `/api/products` |
| Vendors | `/api/vendors` |
| Financial years | `/api/financialyears` |
| Purchases | `/api/purchases` |

Except for authentication and Swagger endpoints, requests require a bearer token:

```http
Authorization: Bearer <jwt-token>
```

## Purchase Workflow

When a purchase invoice is created, the backend validates the vendor, company, vendor-company association, and each product. It then saves the invoice and line items in a transaction and increases inventory. Deleting a purchase reverses the related stock quantities when doing so does not make stock negative.

## Testing

```bash
sh mvnw test
```

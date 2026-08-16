# POS Management System – Backend

A robust **Spring Boot REST API backend** for a full-stack **Point of Sale (POS) Management System**. The backend handles business logic, database operations, authentication, product and company management, and provides RESTful APIs for the React frontend.

## 🚀 Technologies Used

* **Java**
* **Spring Boot**
* **Spring MVC**
* **Spring Data JPA / Hibernate**
* **Spring Security**
* **RESTful APIs**
* **MySQL**
* **Maven**
* **JWT Authentication**
* **Postman**
* **Git & GitHub**

## ✨ Features

* RESTful API architecture
* User authentication and authorization
* JWT-based security
* Company management
* Product management
* Unit management
* CRUD operations
* DTO-based data transfer
* Entity and database relationship management
* Exception handling
* Input validation
* MySQL database integration
* Secure API endpoints
* Frontend integration with React

## 🏗️ Project Architecture

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jbs/posbe/
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       ├── service/
│   │   │       └── PosbeApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔄 Backend Architecture Flow

```text
React Frontend
      ↓
REST API
      ↓
Controller Layer
      ↓
Service Layer
      ↓
Repository Layer
      ↓
MySQL Database
```

## ⚙️ Requirements

Before running the backend, make sure you have:

* Java JDK 17 or later
* Maven
* MySQL Server
* Git

## 🗄️ Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE pos_management;
```

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pos_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Do not commit real database passwords, JWT secrets, email passwords, or other credentials to a public repository.**

## ▶️ Run the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Or using Maven Wrapper

Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend will normally run on:

```text
http://localhost:8080
```

## 🔐 Authentication

The backend uses **Spring Security and JWT** for authentication and authorization.

Typical authentication flow:

```text
User Login
    ↓
Backend validates credentials
    ↓
JWT Token generated
    ↓
Client sends JWT with requests
    ↓
Spring Security validates token
    ↓
Protected API accessed
```

## 📡 API

The backend exposes REST APIs for modules such as:

* Authentication
* Companies
* Products
* Units
* Users
* Other POS operations

The APIs can be tested using **Postman**.

## 🧪 API Testing

A Postman collection is included in the project for testing backend APIs.

You can import the collection into Postman and test:

* Login
* Create
* Read
* Update
* Delete
* Authentication
* Other REST endpoints

## 🔗 Frontend Integration

This backend is designed to work with the React frontend of the POS Management System.

```text
Frontend
React + Vite
        ↓
REST API
        ↓
Backend
Spring Boot
        ↓
Database
MySQL
```

## 🛡️ Error Handling

The application includes centralized exception handling for common API errors such as:

* Invalid input
* Resource not found
* Authentication errors
* Validation errors
* Database-related errors

## 📌 Future Improvements

* Sales and billing management
* Inventory tracking
* Invoice generation
* Advanced reporting
* Role-based permissions
* Payment gateway integration
* Docker deployment
* Cloud deployment

## 👩‍💻 Author

**Taniya Majumder**

B.Tech – Computer Science & Engineering

GitHub: [taniya-232](https://github.com/taniya-232)

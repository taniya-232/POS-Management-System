# POS Management System

A full-stack **Point of Sale (POS) Management System** designed to simplify the management of companies, products, units, users, and other POS-related operations.

The application follows a modern **client-server architecture**, with a React-based frontend communicating with a Spring Boot REST API backend and MySQL database.

## 🚀 Technology Stack

### Frontend

* React.js
* Vite
* JavaScript (ES6+)
* HTML5
* CSS3
* Tailwind CSS
* React Router

### Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT Authentication
* RESTful APIs
* Maven

### Database

* MySQL

### Development & Testing Tools

* Git
* GitHub
* Postman
* VS Code / Eclipse

---

# 🏗️ System Architecture

```text
                       POS MANAGEMENT SYSTEM
                                │
                                ▼
                    ┌──────────────────────┐
                    │    React Frontend    │
                    │      + Vite          │
                    └──────────┬───────────┘
                               │
                               │ HTTP / REST API
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │       Backend        │
                    └──────────┬───────────┘
                               │
                ┌──────────────┴──────────────┐
                │                             │
                ▼                             ▼
       ┌─────────────────┐          ┌─────────────────┐
       │ Spring Security │          │ Business Logic  │
       │      + JWT      │          │ Service Layer   │
       └─────────────────┘          └────────┬────────┘
                                             │
                                             ▼
                                  ┌────────────────────┐
                                  │ Spring Data JPA /  │
                                  │     Hibernate      │
                                  └─────────┬──────────┘
                                            │
                                            ▼
                                  ┌────────────────────┐
                                  │   MySQL Database   │
                                  └────────────────────┘
```

---

# 🔄 Application Flow

```text
User
 │
 ▼
React Frontend
 │
 │ HTTP Request
 ▼
REST Controller
 │
 ▼
Service Layer
 │
 ▼
Repository Layer
 │
 ▼
MySQL Database
 │
 ▼
Repository
 │
 ▼
Service
 │
 ▼
REST Controller
 │
 │ JSON Response
 ▼
React Frontend
 │
 ▼
User Interface
```

---

# 🔐 Authentication Flow

The application uses **Spring Security with JWT-based authentication**.

```text
User
 │
 ▼
Login Page
 │
 ▼
React sends username/password
 │
 ▼
Authentication API
 │
 ▼
Spring Security
 │
 ▼
Credentials validated
 │
 ▼
JWT Token Generated
 │
 ▼
Token stored by Frontend
 │
 ▼
Frontend sends JWT with protected requests
 │
 ▼
JWT Filter validates token
 │
 ▼
Protected REST API
```

---

# 📁 Project Structure

```text
POS-Management-System/
│
├── frontend/
│   │
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── assets/
│   │   ├── App.jsx
│   │   └── main.jsx
│   │
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.js
│   ├── index.html
│   └── README.md
│
├── backend/
│   │
│   ├── .mvn/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── jbs/
│   │   │   │           └── posbe/
│   │   │   │               ├── config/
│   │   │   │               ├── controller/
│   │   │   │               ├── dto/
│   │   │   │               ├── entity/
│   │   │   │               ├── enums/
│   │   │   │               ├── exception/
│   │   │   │               ├── repository/
│   │   │   │               ├── security/
│   │   │   │               ├── service/
│   │   │   │               └── PosbeApplication.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── ...
│   │   │
│   │   └── test/
│   │
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── README.md
│
└── README.md
```

---

# ✨ Main Features

### 👤 Authentication & Security

* User login and authentication
* JWT-based authentication
* Protected REST APIs
* Spring Security integration

### 🏢 Company Management

* Add company
* View company details
* Update company information
* Delete company
* Manage active/inactive company status

### 📦 Product Management

* Add products
* View product details
* Update products
* Delete products
* Associate products with companies
* Manage product-related information

### 📏 Unit Management

* Add units
* View units
* Update units
* Delete units

### 🔄 CRUD Operations

The application provides complete CRUD functionality:

```text
Create
  ↓
Read
  ↓
Update
  ↓
Delete
```

---

# 🧩 Backend Layer Structure

The backend follows a layered architecture:

```text
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
Database
```

### Controller Layer

Handles HTTP requests and API endpoints.

### Service Layer

Contains business logic and application rules.

### Repository Layer

Communicates with the database using Spring Data JPA.

### Entity Layer

Represents database tables using JPA entities.

### DTO Layer

Transfers required data between the frontend and backend.

### Security Layer

Handles JWT authentication and authorization.

### Exception Layer

Handles application and API errors.

---

# 🗄️ Database

The application uses **MySQL** as the relational database.

The backend communicates with MySQL through:

```text
Spring Data JPA
        ↓
Hibernate
        ↓
MySQL
```

Example database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pos_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Security:** Never commit real database passwords, JWT secrets, email passwords, or API keys to a public GitHub repository.

---

# 🔗 Frontend–Backend Communication

```text
React Application
       │
       │ HTTP Request
       ▼
Spring Boot REST API
       │
       ▼
Business Logic
       │
       ▼
MySQL Database
```

The frontend sends requests to backend REST endpoints and receives data in JSON format.

Example:

```text
GET     /api/companies
POST    /api/companies
PUT     /api/companies/{id}
DELETE  /api/companies/{id}
```

The exact endpoint paths may vary depending on the implementation.

---

# ⚙️ Installation & Setup

## Prerequisites

Install the following:

* Java JDK 17+
* Node.js
* npm
* MySQL
* Maven
* Git

---

## 1. Clone the Repository

```bash
git clone https://github.com/taniya-232/POS-Management-System.git
```

```bash
cd POS-Management-System
```

---

## 2. Database Setup

Create the MySQL database:

```sql
CREATE DATABASE pos_management;
```

Configure your database credentials in:

```text
backend/src/main/resources/application.properties
```

---

## 3. Start the Backend

```bash
cd backend
```

Using Maven:

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

Backend:

```text
http://localhost:8080
```

---

## 4. Start the Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

# 🧪 API Testing

The backend REST APIs can be tested using **Postman**.

Typical operations include:

```text
Authentication
     ↓
Create Resource
     ↓
Get Resource
     ↓
Update Resource
     ↓
Delete Resource
```

A Postman collection is included in the backend project where applicable.

---

# 🛡️ Error Handling

The backend provides structured error handling for situations such as:

* Invalid requests
* Validation errors
* Authentication failures
* Resource not found
* Database errors
* Unauthorized access

---

# 📱 Future Enhancements

* Sales and billing management
* Invoice generation
* Inventory management
* Advanced sales reports
* Dashboard analytics
* Role-based permissions
* Payment gateway integration
* Cloud deployment
* Docker support

# 👩‍💻 Author

**Taniya Majumder**

B.Tech – Computer Science & Engineering

GitHub: [taniya-232](https://github.com/taniya-232)

---



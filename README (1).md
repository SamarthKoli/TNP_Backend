# 🎓 TNP Management System – Backend (Spring Boot)

This is the **backend REST API** for the **TNP (Training and Placement) Management System**, developed using **Java and Spring Boot**. It handles operations such as student registration, login, profile management, and admin functionalities for managing placement data.

## ⚙️ Tech Stack

- **Language:** Java  
- **Framework:** Spring Boot  
- **Security:** Spring Security with JWT  
- **Database:** MySQL  
- **ORM:** Spring Data JPA  
- **Build Tool:** Maven

## 📌 Features

- 👨‍🎓 Student registration and login (secured with JWT)
- 🧑‍💼 Admin login and dashboard access
- 📄 Student profile management (update/view)
- 📢 Admin controls: view student list, delete student
- ✅ Role-based access control (`ROLE_STUDENT`, `ROLE_ADMIN`)

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL

### 1. Clone the project
```bash
git clone https://github.com/YourUsername/tnp_backend.git
cd tnp_backend
```

### 2. Configure the database
Edit `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tnp
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and run
```bash
mvn clean install
mvn spring-boot:run
```

## 🔐 API Authentication (JWT)

- After login, you'll receive a JWT token.
- Include this token in the header for all secured endpoints:
```http
Authorization: Bearer <your_token_here>
```

## 📮 API Endpoints

### 🧑 Student

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/api/register`           | Register a new student   |
| POST   | `/api/login`              | Login and get JWT token  |
| GET    | `/api/profile`            | Get student profile      |
| PUT    | `/api/profile/update`     | Update student profile   |

### 🧑‍💼 Admin

| Method | Endpoint                  | Description                  |
|--------|---------------------------|------------------------------|
| POST   | `/api/admin/login`        | Admin login                  |
| GET    | `/api/admin/students`     | Get list of all students     |
| DELETE | `/api/admin/delete/{id}`  | Delete a student by ID       |

> Note: Some endpoints require `ROLE_ADMIN`, others `ROLE_STUDENT`.

## 📌 Sample Login Request

```json
POST /api/login
{
  "email": "student@gmail.com",
  "password": "1234"
}
```

## 📚 Database Schema (Entities)

- `Student`: ID, name, email, phone, password, department, etc.
- `Admin`: ID, email, password
- JWT tokens used for session authentication

## 📬 Contact

For any queries or issues, feel free to contact:  
**Samarth Koli**  
GitHub: [github.com/SamarthKoli](https://github.com/SamarthKoli)
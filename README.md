# bibliotheca58

A library management system built with **Spring Boot** to manage books, 
authors, members, and loans effectively. Bibliotheca58 exposes a RESTful API
for seamless integration and interaction, accompanied by an interactive Swagger-UI 
for API documentation.

---
### Features

- **Book Management**:
    - Add, update, delete, and retrieve book details.
    - The library has only one copy of each book.
    - Filter books by genre, title or price range.
  
- **Author Management**:
    - Manage authors with CRUD operations.
    - Associate books with authors.
  
- **Member Management**:
    - Add, update, delete, and retrieve member details.
  
- **Loan Management**:
  - Issue loans for members with a maximum limit of 5 books at a time.
  - Return loans by updating the return date.
  - Retrieve loans by member ID.
  
- **Interactive API Documentation**:
    - Swagger UI for API exploration.
    - OpenAPI documentation available in JSON format.
---
## Technologies Used

- **Backend**:
    - Java 17+
    - Spring Boot 3.x
    - Spring Data JPA
    - PostgreSQL
- **API Documentation**:
    - Springdoc OpenAPI (`springdoc-openapi-starter-webmvc-ui`)
- **Build Tools**:
    - Gradle
- **Containerization**:
    - Docker & Docker Compose
- **Testing**:
    - JUnit 5
    - Spring Boot Test
---

## Setup and Installation

This application can be run with a single **`docker-compose up`** command. Docker Compose manages both the **Java application** and the **PostgreSQL database** as services.


### Prerequisites

- [Docker](https://www.docker.com/get-started) installed and running.
- [Docker Compose](https://docs.docker.com/compose/install/) installed.

#### **1. Clone the Repository**

```bash
git clone https://github.com/christlain-nanko/bibliotheca58.git
cd bibliotheca58 
```
#### **2. Environment Configuration**

To set up your local environment:

- Copy the `.env.example` file and rename it to `.env`:
   ```bash
   cp .env.example .env
   ```
- Replace placeholder values (<your-database-username>, etc.) with your credentials.
- Ensure the .env file is in the same directory as docker-compose.yml.

#### **3. Build and Start the Application**

The pre-configured docker-compose.yml file defines the PostgreSQL database and the Java Spring Boot application as services.

To build and start the application, simply run:

```bash
docker compose up --build -d
```
- The application will be accessible at http://localhost:8080.
- Swagger UI will be available at http://localhost:8080/swagger-ui.html.

#### **4. Stopping the Application**

To stop the running services:

```bash
docker compose down
```

#### **5. Testing the Application**
Run unit tests:

```bash
./gradlew test
```

---
## Future Enhancements

- Role-Based Access Control
- implement notification functionality for overdue loans.
- Pagination and Sorting
- Book Reservation System
- AI-Powered Recommendations
- increase Tests Coverage

---
## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.


---
## License

This project is licensed under the MIT License.









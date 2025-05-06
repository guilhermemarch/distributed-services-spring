# Microservices with Java Spring Boot and OpenFeign

This project demonstrates a distributed system architecture using two microservices that work together to provide an integrated solution. The system follows a client-server pattern where:

- **MicroserviceA** (Port 8080): Acts as the client-facing service, handling incoming HTTP requests and delegating them to MicroserviceB
- **MicroserviceB** (Port 8081): Communicates with the JsonPlaceholder API and manages data persistence in MongoDB

## Contributors

* Gabriel Felipe Birck
* Guilherme Marschall
* João Pedro Ferreira Ponchiroli
* Matheus Gotardo Reato
* Ruan Carlos Dalla Rosa



## Prerequisites

Before you begin, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6 or higher
- MongoDB 4.4 or higher
- IntelliJ IDEA (recommended) or any Java IDE
- Postman (for API testing)

## Project Structure

```
distributed-services-spring/
├── microserviceA/           # Client-facing service (Port 8080)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── microserviceB/           # Data service (Port 8081)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
└── swagger.json            # API documentation
```

## Features

### Posts Management
- Create new posts
- Retrieve all posts
- Get post by ID
- Update existing posts
- Delete posts

### Comments Management
- Create new comments
- Retrieve all comments
- Get comment by ID
- Update existing comments
- Delete comments

## Technologies

- **Backend Framework**: Spring Boot 3.4.0
- **Java Version**: 17
- **Database**: MongoDB
- **API Documentation**: Swagger/OpenAPI
- **Service Communication**: OpenFeign
- **Build Tool**: Maven
- **Development Tools**:
    - IntelliJ IDEA
    - Postman
    - Git/GitHub

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/DallaProRacing/Desafio2_Lord-of-Springs.git
cd Desafio2_Lord-of-Springs
```

### 2. Database Setup
1. Ensure MongoDB is running on port 27017
2. If using a different port, update the following files:
    - `microserviceB/src/main/resources/application.properties`
    - `microserviceB/src/test/resources/application-test.properties`

### 3. Build the Project
```bash
# Build MicroserviceA
cd microserviceA
mvn clean install

# Build MicroserviceB
cd ../microserviceB
mvn clean install
```

## API Documentation

The API documentation is available in Swagger format. To view it:

1. Download the `swagger.json` file from the project root
2. Visit [Swagger Editor](https://editor.swagger.io/)
3. Import the `swagger.json` file
4. Explore the available endpoints and their specifications

## Usage Guide

### Running the Services

1. Start MongoDB:
   ```bash
   mongod --port 27017
   ```

2. Start MicroserviceB (Port 8081):
   ```bash
   cd microserviceB
   mvn spring-boot:run
   ```

3. Start MicroserviceA (Port 8080):
   ```bash
   cd microserviceA
   mvn spring-boot:run
   ```

### Initial Data Setup

1. Using Postman or any HTTP client, make the following requests:
   ```bash
   # Sync posts data
   POST http://localhost:8081/api/posts/syncData

   # Sync comments data
   POST http://localhost:8081/api/posts/syncDataComments
   ```

2. Verify the data in MongoDB:
    - Open MongoDB Compass or your preferred MongoDB client
    - Connect to `mongodb://localhost:27017`
    - Check the `Desafio2_Lord-of-Springs` database

### Testing the API

Once the services are running and data is synced, you can:
- Create new posts/comments
- Retrieve existing data
- Update records
- Delete records

Refer to the Swagger documentation for detailed endpoint specifications.

## Contributors

* Gabriel Felipe Birck
* Guilherme Marschall
* João Pedro Ferreira Ponchiroli
* Matheus Gotardo Reato
* Ruan Carlos Dalla Rosa

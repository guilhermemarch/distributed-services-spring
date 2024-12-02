# Microservices with Java Spring Boot and OpenFeign
This project involved the development of two microservices designed to work together, providing an integrated solution. MicroserviceB communicates with JsonPlaceholder API, managing interactions with its endpoints. Meanwhile, MicroserviceA serves as the client-facing 
service, delegating HTTP requests (POST, GET, PUT, DELETE) to MicroserviceB, which processes and executes them.


# Colaborators
* Gabriel Felipe Birck
* Guilherme Marschall
* João Pedro Ferreira Ponchiroli
* Matheus Gotardo Reato
* Ruan Carlos Dalla Rosa

## Functionalities
### Post
* Create
* Read
* Update
* Delete
### Comments
* Create
* Read
* Update
* Delete

## Utilized Technologies
* IntelliJ IDEA
* Java 17
* Spring Boot 3
* OpenFeign
* MongoDB
* Postman
* Github

## Configuration

### Clone repository
This command will clone the repository to the directory where it is executed.
```
git clone https://github.com/DallaProRacing/Desafio2_Lord-of-Springs.git
```

### Database creation
The document will be created when you perform the HTTP POST request to syncData. You will need to refresh the localhost page for the document to appear.

Be aware that the port used for MongoDB is 27017. If you are using a different port, you can update it in the application.properties file of MicroserviceB and in the application-test.properties file located in the test directory of MicroserviceB.

## API Documentation with Swagger UI
* Download the swagger.json File
* Access the Swagger Editor. Visit the Swagger Editor website: https://editor.swagger.io/.
* Import the Swagger JSON File
* Click on File in the menu.
* Select Import File.
* Locate and upload the swagger.json file you downloaded.
* Explore the API


## How to use
* Open the IntelliJ IDEA
* Run MicroserviceA application
* Run MicroserviceB application
* Go to Postman
* Make a POST request to ``` http://localhost:8081/api/posts/syncData ```
* Make aother POST request to ``` http://localhost:8081/api/posts/syncDataComments ```
* Refresh your MongoDB localhost page, the document and the collections of this application should appear
* Now you have a populated document and can perform any desired operations on it

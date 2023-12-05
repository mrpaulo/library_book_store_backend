# Library Book Store Backend
This project is the backend for a library book store application. It provides an API for managing books, authors, publishers and users, as well as handling user authentication and authorization. The API is built using Java Spring Boot and includes unit and integration tests written in Groovy Spock.
Also, this is an ongoing project, so fix bugs, improvements, and new features are coming soon.
## Technologies Used
- **Java 11**: A programming language that runs on a virtual machine and provides a wide range of features for building applications.
- **Spring Boot**: A framework for building web applications that simplifies the setup and configuration of a Spring-based application.
- **Groovy Spock**: A testing framework that uses Groovy syntax and provides a highly readable and expressive way to write tests.
- **Maven**: A build automation tool used for managing dependencies and building Java projects.
- **PostgreSQL**: An open-source relational database management system that is widely used in production environments.
- **Spring Data JPA**: A framework that provides a simple and consistent way to interact with databases using Java Persistence API (JPA) specifications.
- **Spring Data Security**: A Spring module that provides security features such as authentication and authorization.
- **OAuth2**: A protocol used for authorization that allows applications to access user data from other applications.
- **Basic Auth**: A simple authentication scheme that sends a username and password in the request header.
- **Bcrypt**: A password hashing function used for securing passwords in databases.
- **Lombok**: A Java library that provides annotations to generate boilerplate code for Java classes.
- **Flyway**: A database migration tool used for versioning and managing database schema changes.
- **Swagger**: A set of tools used for documenting and testing RESTful APIs.
- **Model Mapper**: A Java library used for mapping objects between different data models.
- **Log4j2**: A Java-based logging utility used for generating logs during application execution.

## Features
- User authentication and authorization using JWT tokens
- CRUD operations for managing books, authors, publishers and users
- Integration tests to ensure proper functionality of the API
- Unit tests to ensure that individual components are working as expected
- Robust error handling and logging

## Getting Started
- Clone the repository: `git clone https://github.com/mrpaulo/library_book_store_backend.git
- Install dependencies:  `mvn install`
- Run the application: `mvn spring-boot:run`


## REST API Endpoints

### Authentication
- POST /oauth/token: Authenticate a user and return a JWT token
- POST /api/v1/authentications/logout: Logout the app
- POST /oauth/check_token: check if it is a valid token
### Books
- GET /api/v1/books: Get all books
- GET /api/v1/books/{id}: Get a specific book by ID
- POST /api/v1/books: Add a new book
- POST /api/v1/books/fetch: Returns a pageable list of books
- PUT /api/v1/books/{id}: Update a book by ID
- DELETE /api/v1/books/{id}: Delete a book by ID
### Authors
- GET /api/v1/authors: Get all authors
- GET /api/v1/authors/{id}: Get a specific author by ID
- POST /api/v1/authors: Add a new author
- POST /api/v1/authors/fetch: Returns a pageable list of authors
- PUT /api/v1/authors/{id}: Update an author by ID
- DELETE /api/v1/authors/{id}: Delete an author by ID
### Publishers
- GET /api/v1/publishers: Get all publishers
- GET /api/v1/publishers/{id}: Get a specific publisher by ID
- POST /api/v1/publishers: Add a new publisher
- POST /api/v1/publishers/fetch: Returns a pageable list of publishers
- PUT /api/v1/publishers/{id}: Update an publisher by ID
- DELETE /api/v1/publishers/{id}: Delete an publisher by ID
### Users
- GET /api/v1/users: Get all users
- GET /api/v1/users/{id}: Get a specific user by ID
- POST /api/v1/users: Add a new user
- POST /api/v1/users/fetch: Returns a pageable list of users
- PUT /api/v1/users/{id}: Update a user by ID
- DELETE /api/v1/users/{id}: Delete a user by ID

## Testing
To run the unit tests, run `mvn test`. To run the integration tests, run `mvn integration-test`.

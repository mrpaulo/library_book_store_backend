# Library Book Store Backend
This project is the backend for a library book store application. It provides an API for managing books, authors, and users, as well as handling user authentication and authorization. The API is built using Java Spring Boot and includes unit and integration tests written in Groovy Spock.

## Technologies Used
- Java 11
- Spring Boot
- Groovy Spock
- Maven
- PostgreSQL
- Spring Data JPA
- Spring Data Security
- OAuth2
- Basic Auth
- Bcrypt
- Lombok
- Flyaway Migrations
- Swagger 
- Model Mapper
- Log4j2

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
- POST /api/auth/login: Authenticate a user and return a JWT token
- POST /api/auth/logout: Logout the app
### Books
- GET /api/books: Get all books
- GET /api/books/{id}: Get a specific book by ID
- POST /api/books: Add a new book
- PUT /api/books/{id}: Update a book by ID
- DELETE /api/books/{id}: Delete a book by ID
### Authors
- GET /api/authors: Get all authors
- GET /api/authors/{id}: Get a specific author by ID
- POST /api/authors: Add a new author
- PUT /api/authors/{id}: Update an author by ID
- DELETE /api/authors/{id}: Delete an author by ID
### Publishers
- GET /api/publishers: Get all publishers
- GET /api/publishers/{id}: Get a specific publisher by ID
- POST /api/publishers: Add a new publisher
- PUT /api/publishers/{id}: Update an publisher by ID
- DELETE /api/publishers/{id}: Delete an publisher by ID
### Users
- GET /api/users: Get all users
- GET /api/users/{id}: Get a specific user by ID
- POST /api/users: Add a new user
- PUT /api/users/{id}: Update a user by ID
- DELETE /api/users/{id}: Delete a user by ID

## Testing
To run the unit tests, run `mvn test`. To run the integration tests, run `mvn integration-test`.

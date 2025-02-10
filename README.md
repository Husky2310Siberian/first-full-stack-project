# Case Study - Book Network

### 📖 Information

<ul style="list-style-type:disc">
  <li><b>Book Network</b> is a <b>Spring Boot application</b> covering important and useful features, it is a comprehensive web application designed to help users, manage their book collections and interact with a community of book lovers. Users can create, update, share, borrow, and return books in a secure environment. The platform ensures a smooth and engaging user experience while maintaining robust security and best practices in API design  .</li> 
  <li>
    <b>Roles:</b>
    <ul>
      <li><b>User:</b> Users with privileges, create, update, share, and archive their books.</li>
    </ul>
    <b>Feature:</b>
    <ul>
      <li><b>User Registration:</b> Users can register for a new account.</li>
      <li><b>Email Validation:</b> Accounts are activated using secure email validation codes.</li>
      <li><b>User Authentication:</b>Existing users can log in to their accounts securely.</li>
      <li><b>Book Management:</b> Users can create, update, share, and archive their books.</li>
      <li><b>Book Borrowing:</b> Implements necessary checks to determine if a book is borrowable.</li>
      <li><b>Book Returning:</b> Users can return borrowed books.</li>
      <li><b>Book Return Approval:</b> Functionality to approve book returns.</li>
    </ul>
  </li>
</ul>


### Technologies

## Backend
- Java 17
- Spring Boot 3.0
- Spring Security
- JWT
- Restful API
- Lombok
- Maven
- Docker
- Docker Compose
- CI/CD (Github Actions)
- Postman
- Open Api (Swagger 3)
- POSTGRESQL

## Frontend
-  Angular
-  Component-Based Architecture
-  Lazy Loading
-  Authentication Guard
-  OpenAPI Generator for Angular
-  Bootstrap

### SETUP INSTRUCTIONS

### Backend

1. Clone the repository
```
https://github.com/Husky2310Siberian/first-full-stack-project.git
```
2. Run the docker-compose file
```
docker-compose up -d
```
3. Navigate to the app directory 
```
cd book-network
```

4. Install dependencies
```
mvn clean install
```

5. Run the application
```
java -jar target/book-network-api 
```

6.Use Swagger via URL
```
Open a web browser and go to `http://localhost:8088/swagger-ui/index.html.
```

### Frontend


1. Development Server
```
Run ng serve for a dev server. Navigate to http://localhost:4200/.
```
2. Code scafoldind
```
Run ng generate component component-name to generate a new component.
```
3. Build
```
Run ng build to build the project.
```

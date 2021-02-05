# Spring Boot JPA MySQL - BackEnd APP for Crescendia Test

## Run Spring Boot application
```
mvn spring-boot:run
```
### Google Cloud Vision service account JSON file 
See [Creating and managing service account keys](https://cloud.google.com/iam/docs/creating-managing-service-account-keys/).

### Application.Properties
```
spring.datasource.url= jdbc:mysql://localhost:3306/{yourdatabase}?useSSL=false

spring.datasource.username= {databaseusername}

spring.datasource.password= {databasepassword}

spring.cloud.gcp.credentials.location=file:src/main/resources/{yourgoogleserviceaccountjsonfile}

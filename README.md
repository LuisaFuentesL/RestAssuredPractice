# RestAssured Practice

This project is designed for practicing API testing using RestAssured, Java, Maven, POJO, and Cucumber. 
The following README provides information on the project structure, dependencies, and instructions for running the test cases.

## Tools and Technologies

- Java
- Maven
- RestAssured
- POJO
- Cucumber

## Setup

### Dependencies

- Java 8 or higher
- Maven

### Clone the Repository

```bash
git clone https://github.com/your-username/restassured-practice.git
cd restassured-practice
```

### Project Structure

The project structure is organized as follows:

```plaintext
restassured-practice
|-- src
|   |-- main
|       |-- java
|           `-- com
|               `-- testing
|                    `-- api
|                        |-- models
|                        |-- request
|                        `-- utils
|       |-- resources
|           `-- data
|
|   |-- test
|       |-- java
|           `-- com
|               `-- testing
|                    `-- api
|                       |-- runner
|                       `-- stepDefinitions
|       |-- resources
|           |-- features
|           `-- schemas
|               |-- client
|               |-- resource
|               `-- log4j2.properties
|-- target
|-- pom.xml
|-- .gitignore
|-- README.md
```

- `src/main/java`: Contains utility classes and helper functions.
- `src/test/java`: Houses the test cases, API configuration, and step definitions for Cucumber.
- `pom.xml`: Maven project configuration file.

## Configuration

The base URL is `https://63b6dfe11907f863aa04ff81.mockapi.io`.

## Test Cases

### Test Case 1: Get the List of Clients

Description: Verify that the list of all clients can be retrieved correctly from the "/api/v1/clients" endpoint.

Conditions:
- The "/api/v1/clients" endpoint exists and is operational.
- There must be at least 3 clients in the system.

Verifications:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.

### Test Case 2: Get the List of Resources

Description: Verify that the list of all resources can be retrieved correctly from the "/api/v1/resources" endpoint.

Conditions:
- The "/api/v1/resources" endpoint exists and is operational.
- There must be at least 5 resources in the system.

Verifications:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.

### Test Case 3: Create a New Client

Description: Verify that a new client can be created successfully at the "/api/v1/clients" endpoint.

Conditions:
- The "/api/v1/clients" endpoint exists and is operational.
- Data for creating a new client is available.

Verifications:
- Verify that the response is equal to an HTTP status code of 201.
- Verify the structure of the response body schema.
- Verify the response body data.

### Test Case 4: Update the Last Resource

Description: Verify that an existing resource can be updated successfully at the "/api/v1/resources" endpoint.

Conditions:
- The "/api/v1/resources" endpoint exists and is operational.
- There must be at least 5 resources in the system.
- Data for updating the last resource is available.

Verifications:
- Verify that the response is equal to an HTTP status code of 200.
- Verify the structure of the response body schema.
- Verify the response body data.


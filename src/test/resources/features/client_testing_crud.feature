@active
Feature: Client testing CRUD

  @smoke
  Scenario: Create a new client
    Given I have a client with the following details:
      |  Name  |  LastName  |  Country |  City   |        Email      |     Phone      |
      |  Luisa | Fuentes    | Colombia |  Bogotá |  luisa@email.com  | 316-74-56-020  |
    When I send a POST request to create a client
    Then the response should have a status code of 201 for the client
    And the response should include the details of the created client
    And validates the response with client JSON schema for the client

  @smoke
  Scenario: View all the clients
    Given there are at least 3 registered clients in the system
    When I send a GET request to view all the clients
    Then the response should have a status code of 200 for the client
    And validates the response with client list JSON schema for the clients



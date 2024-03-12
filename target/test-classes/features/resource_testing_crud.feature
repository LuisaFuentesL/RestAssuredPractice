@active
Feature: Resource testing CRUD

  @smoke
  Scenario: View all the resources
    Given there are at least 5 registered resources in the system
    When I send a GET request to view all the resources
    Then the response should have a status code of 200 for the resource
    And validates the response with resource list JSON schema for the resources

  @smoke
  Scenario: Update the last resource
    Given there are at least 5 registered resources in the system
    And I retrieve the details of the latest resource
    When I send a PUT request to update the latest resource
    """
    {
      "name": "Smartphone X",
      "trademark": "TechCo",
      "stock": 500,
      "price": 599.99,
      "description": "The latest model of smartphone with advanced features and a high-resolution camera.",
      "tags": "Technology, Electronics, Smartphone",
      "active": true
    }
    """
    Then the response should have a status code of 200 for the resource
    And the response should have the following details:
      |    name      | trademark | stock | price  |                        description                                                  |                  tags               | active  |
      | Smartphone X |  TechCo   |  500  | 599.99 | The latest model of smartphone with advanced features and a high-resolution camera. | Technology, Electronics, Smartphone |   true  |
    And validates the response with resource JSON schema for the resource



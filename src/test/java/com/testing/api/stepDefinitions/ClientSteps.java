package com.testing.api.stepDefinitions;

import com.testing.api.models.Client;
import com.testing.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);
    private final ClientRequest clientRequest = new ClientRequest();
    private Response response;
    private Client client;

    /**
     * Given step definition for initializing a client with the provided details from a DataTable.
     * @param clientData clientData DataTable containing client details.
     */
    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable clientData) {
        Map<String, String> clientDataMap = clientData.asMaps().get(0);
        client = Client.builder()
                        .name(clientDataMap.get("Name"))
                        .lastName(clientDataMap.get("LastName"))
                        .country(clientDataMap.get("Country"))
                        .city(clientDataMap.get("City"))
                        .email(clientDataMap.get("Email"))
                        .phone(clientDataMap.get("Phone"))
                        .build();
        logger.info("Client mapped: " + client);
    }

    /**
     * Given step definition to ensure there are at least a specified number of registered clients in the system.
     */
    @Given("there are at least {int} registered clients in the system")
    public void thereAreRegisteredClientsInTheSystem() {
        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientList = clientRequest.getClientsEntity(response);
        if (clientList.size()<3){
            for (int i = 0; i<3; i ++){
                response = clientRequest.createDefaultClient();
                logger.info(response.statusCode());
                Assert.assertEquals(201, response.statusCode());
            }
        }
        logger.info("There are more than 3 registered clients in the system");
    }

    /**
     * When step definition for sending a POST request to create a new client.
     */
    @When("I send a POST request to create a client")
    public void iSendAPOSTRequestToCreateAClient() {
        response = clientRequest.createClient(client);
    }

    /**
     * When step definition for sending a GET request to obtain all clients.
     */
    @When("I send a GET request to view all the clients")
    public void iSendAGETRequestToViewAllTheClients() {
        response = clientRequest.getClients();
    }

    /**
     * Then step definition to verify that the response has a specific status code.
     * @param statusCode The expected status code to be compared with the actual response status code.
     */
    @Then("the response should have a status code of {int} for the client")
    public void theResponseShouldHaveAStatusCodeForTheClient(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    /**
     * And step definition to validate the response with the client JSON schema for a client.
     */
    @And("validates the response with client JSON schema for the client")
    public void userValidatesResponseWithClientJSONSchemaForTheClient() {
        String path = "schemas/client/clientSchema.json";
        Assert.assertTrue(clientRequest.validateSchema(response, path));
    }

    /**
     * And step definition to verify that the response includes the details of the created client.
     */
    @And("the response should include the details of the created client")
    public void theResponseShouldIncludeTheDetailsOfTheCreatedClient() {
        Client newClient = clientRequest.getClientEntity(response);
        newClient.setId(null);
        Assert.assertEquals(client, newClient);
    }

    /**
     * And step definition to validate the response with the client list JSON schema.
     */
    @And("validates the response with client list JSON schema for the clients")
    public void userValidatesResponseWithClientListJSONSchema() {
        String path = "schemas/client/clientListSchema.json";
        Assert.assertTrue(clientRequest.validateSchema(response, path));
    }
}

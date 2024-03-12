package com.testing.api.requests;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonFileReader;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientRequest extends BaseRequest {
    private String endpoint;

    /**
     * Retrieves a list of clients by sending a GET request to the endpoint.
     * @return Response object containing the result of the GET request.
     */
    public Response getClients() {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * Creates a client by sending a POST request with the provided client details.
     * @param client The Client object containing details for creating a new client.
     * @return Response object containing the result of the client creation request.
     */
    public Response createClient(Client client) {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestPost(endpoint, createBaseHeaders(), client);
    }

    /**
     * Converts the JSON representation of a client from the provided Response object into a Client entity.
     * @param response The Response object containing the JSON representation of the client.
     * @return Client object representing the client entity parsed from the response.
     */
    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }

    /**
     * Parses client entities from the JSON representation in the provided Response object into a List of Client objects.
     * @param response The Response object containing the JSON representation of clients.
     * @return List of Client objects representing the entities parsed from the response.
     */
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

    /**
     * Creates a default client by reading client details from a JSON file and sending a request.
     * @return Response object containing the result of the client creation request.
     */
    public Response createDefaultClient() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createClient(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }

    /**
     * Validates the response against a JSON schema located at the specified path.
     * @param response    The Response object to be validated against the JSON schema.
     * @param schemaPath  The path to the JSON schema for validation.
     * @return True if the response passes the schema validation; false otherwise.
     */
    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true; // Return true if the assertion passes
        } catch (AssertionError e) {
            // Assertion failed, return false
            return false;
        }
    }
}

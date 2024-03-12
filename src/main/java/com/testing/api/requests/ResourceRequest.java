package com.testing.api.requests;

import com.google.gson.Gson;
import com.testing.api.models.Resource;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonFileReader;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceRequest extends BaseRequest {
    private String endpoint;
    private static final Logger logger = LogManager.getLogger(ResourceRequest.class);

    /**
     * Retrieves the details of a specific resource by sending a GET request with the provided resource ID.
     * @param resourceId The ID of the resource to retrieve.
     * @return Response object containing the details of the requested resource.
     */
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * Retrieves a list of resources by sending a GET request to the endpoint.
     * @return Response object containing the result of the GET request.
     */
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * Creates a resource by sending a POST request with the provided resource details.
     * @param resource The Resource object containing details for creating a new resource.
     * @return Response object containing the result of the resource creation request.
     */
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }

    /**
     * Retrieves the details of the latest resource from the system.
     * @return Resource object representing the latest resource.
     */
    public Resource getLastResource() {
        Response response = getResources();
        List<Resource> resources = getResourcesEntity(response);
        return resources.get(resources.size() - 1);
    }

    /**
     * Updates a specific resource by sending a PUT request with the provided updated resource details and resource ID.
     * @param requestResource The updated Resource object containing the new details.
     * @param resourceId      The ID of the resource to be updated.
     * @return Response object containing the result of the resource update request.
     */
    public Response updateResource(Resource requestResource, String resourceId){
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), requestResource);

    }

    /**
     * Converts the JSON representation of a resource from the provided Response object into a Resource entity.
     * @param response The Response object containing the JSON representation of the resource.
     * @return Resource object representing the resource entity parsed from the response.
     */
    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    /**
     * Parses resource entities from the JSON representation in the provided Response object into a List of Resource objects.
     * @param response The Response object containing the JSON representation of resources.
     * @return List of Resource objects representing the entities parsed from the response.
     */
    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    /**
     * Creates a default resource by reading resource details from a JSON file and sending a request.
     * @return Response object containing the result of the resource creation request.
     */
    public Response createDefaultResource() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createResource(jsonFile.getResourceByJson(Constants.DEFAULT_RESOURCE_FILE_PATH));
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

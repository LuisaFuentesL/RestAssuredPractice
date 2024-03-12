package com.testing.api.stepDefinitions;

import com.google.gson.Gson;
import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
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

public class ResourceSteps {
    private static final Logger logger = LogManager.getLogger(ResourceSteps.class);
    private final ResourceRequest resourceRequest = new ResourceRequest();
    private Response response;
    private Resource resource;

    /**
     * Given step definition to ensure there are at least a specified number of registered resources in the system.
     */
    @Given("there are at least {int} registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem() {
        response = resourceRequest.getResources();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());

        List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
        if (resourceList.size()<5){
            for (int i = 0; i<5; i ++){
                response = resourceRequest.createDefaultResource();
                logger.info(response.statusCode());
                Assert.assertEquals(201, response.statusCode());
            }
        }
        logger.info("There are more than 5 registered resources in the system");
    }

    /**
     * And step definition to retrieve the details of the latest resource by sending a GET request.
     */
    @And("I retrieve the details of the latest resource")
    public void sendGETRequest(){
        Resource latestResource = resourceRequest.getLastResource();
        String resourceId = latestResource.getId();
        response = resourceRequest.getResource(resourceId);
        logger.info("Details of the latest resource: ");
        logger.info(response.jsonPath().prettify());
    }

    /**
     * When step definition for sending a GET request to obtain all resources.
     */
    @When("I send a GET request to view all the resources")
    public void iSendAGETRequestToViewAllTheResources() {
        response = resourceRequest.getResources();
    }

    /**
     * When step definition for sending a PUT request to update the information of the latest resource.
     */
    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheLatestResource(String requestBody) {
         Resource latestResource= resourceRequest.getLastResource();
         String resourceId = latestResource.getId();
         Gson gson = new Gson();
         Resource requestedResource = gson.fromJson(requestBody, Resource.class);
         response = resourceRequest.updateResource(requestedResource, resourceId);
         logger.info("Latest resource updated: ");
         logger.info(resourceRequest.getResourceEntity(response));
    }

    /**
     * Then step definition to verify that the response has a specific status code.
     * @param statusCode The expected status code to be compared with the actual response status code.
     */
    @Then("the response should have a status code of {int} for the resource")
    public void theResponseShouldHaveAStatusCodeForTheResource(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    /**
     * And step definition to verify that the response has the expected details as specified in the DataTable.
     * @param expectedData DataTable containing the expected details.
     */
    @And("the response should have the following details:")
    public void theResponseShouldHaveTheFollowingDetails(DataTable expectedData){
        resource = resourceRequest.getResourceEntity(response);
        Map<String, String> expextedDataMap = expectedData.asMaps().get(0);

        Assert.assertEquals(expextedDataMap.get("name"), resource.getName());
        Assert.assertEquals(expextedDataMap.get("trademark"), resource.getTrademark());
        Assert.assertEquals(expextedDataMap.get("stock"), String.valueOf(resource.getStock()));
        Assert.assertEquals(expextedDataMap.get("price"), String.valueOf(resource.getPrice()));
        Assert.assertEquals(expextedDataMap.get("description"), resource.getDescription());
        Assert.assertEquals(expextedDataMap.get("tags"), resource.getTags());
        Assert.assertEquals(expextedDataMap.get("active"), String.valueOf(resource.isActive()));
    }

    /**
     * And step definition to validate the response with the resource JSON schema for a resource.
     */
    @And("validates the response with resource JSON schema for the resource")
    public void userValidatesResponseWithResourceJSONSchema() {
        String path = "schemas/resource/resourceSchema.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, path));
    }

    /**
     * And step definition to validate the response with the resource list JSON schema.
     */
    @And("validates the response with resource list JSON schema for the resources")
    public void userValidatesResponseWithResourceListJSONSchemaForTheResource() {
        String path = "schemas/resource/resourceListSchema.json";
        Assert.assertTrue(resourceRequest.validateSchema(response, path));
    }
}

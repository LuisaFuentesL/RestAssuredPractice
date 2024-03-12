package com.testing.api.requests;

import com.testing.api.utils.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class BaseRequest {
    private static final Logger logger = LogManager.getLogger(ResourceRequest.class);

    /**
     * Sends a GET request to the specified API endpoint using Rest Assured.
     * @param endpoint The API URL to send the GET request to.
     * @param headers  A map of headers to include in the request.
     * @return Response object containing the result of the GET request.
     */
    protected Response requestGet(String endpoint, Map<String, ?> headers) {
        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .when()
                .get(endpoint);
    }

    /**
     * Sends a POST request to create a new element at the specified API endpoint using Rest Assured.
     * @param endpoint The API URL to send the POST request to.
     * @param headers  A map of headers to include in the request.
     * @param body     The model object representing the body of the POST request.
     * @return Response object containing the result of the POST request.
     */
    protected Response requestPost(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                .contentType(Constants.VALUE_CONTENT_TYPE)
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint);
    }

    /**
     * Sends a PUT request to update an element at the specified API endpoint using Rest Assured.
     * @param endpoint The API URL to send the PUT request to.
     * @param headers  A map of headers to include in the request.
     * @param body     The model object representing the body of the PUT request.
     * @return Response object containing the result of the PUT request.
     */
    protected Response requestPut(String endpoint, Map<String, ?> headers, Object body) {
        return RestAssured.given()
                        .contentType(Constants.VALUE_CONTENT_TYPE)
                        .headers(headers)
                        .body(body)
                        .when()
                        .put(endpoint);
    }

    /**
     * Creates and returns a map of base headers with content type information.
     * @return Map of base headers with content type information.
     */
    protected Map<String, String> createBaseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.VALUE_CONTENT_TYPE);
        return headers;
    }
}

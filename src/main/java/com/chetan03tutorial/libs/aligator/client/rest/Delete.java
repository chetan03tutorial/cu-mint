package com.chetan03tutorial.libs.aligator.client.rest;

import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Delete extends AbstractHttpProtocol {

    public Response call(RequestContext request) {
        RequestSpecification requestBuilder = getRequestBuilder(request);
        return requestBuilder.delete(request.getEndPoint()).thenReturn();
    }
}

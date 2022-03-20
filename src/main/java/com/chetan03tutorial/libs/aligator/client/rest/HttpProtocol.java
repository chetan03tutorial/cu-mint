package com.chetan03tutorial.libs.aligator.client.rest;

import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import io.restassured.response.Response;

public interface HttpProtocol {
    Response call(RequestContext context);
}

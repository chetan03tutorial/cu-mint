package com.chetan03tutorial.libs.aligator.client.rest;

import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Post extends AbstractHttpProtocol {

    public Response call(RequestContext context){
        RequestSpecification requestBuilder = getRequestBuilder(context);
        return requestBuilder.log().all()
                .when().post(context.getEndPoint())
                .then().log().all()
                .extract().response();
    }

}

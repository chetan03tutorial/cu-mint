package com.chetan03tutorial.libs.aligator.client.rest;

import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import com.chetan03tutorial.libs.aligator.util.WebserverHelper;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class AbstractHttpProtocol implements HttpProtocol {

    public RequestSpecification getRequestBuilder(RequestContext request){
        RequestSpecification requestBuilder = given();
        requestBuilder.port(WebserverHelper.getPort());
        requestBuilder.contentType(ContentType.JSON);
        requestBuilder.basePath(WebserverHelper.getContextPath());
        Map<String, String> pathParam = request.getPathParams();
        Map<String, String> formParam = request.getFormParams();
        Map<String, String> headerParam = request.getHeaders();
        Map<String, File> multipartParam = request.getMultipartParams();
        Map<String, Object> queryParam = request.getQueryParams();

        if (pathParam.size() != 0) {
            requestBuilder.pathParams(pathParam);
        }
        if (formParam.size() != 0) {
            requestBuilder.formParams(formParam);
        }
        if (request.getRequestBody() != null) {
            requestBuilder.body(request.getRequestBody());
        }
        if (headerParam.size() != 0) {
            requestBuilder.headers(headerParam);
        }

        if (queryParam.size() != 0) {
            requestBuilder.queryParams(queryParam);
        }
        if (multipartParam.size() != 0) {
            for (Map.Entry<String, File> fileMap : multipartParam.entrySet()) {
                requestBuilder.multiPart(fileMap.getKey(), fileMap.getValue());
            }
        }
        return requestBuilder;
    }
}

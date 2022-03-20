package com.chetan03tutorial.libs.aligator.client.rest;

import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import com.chetan03tutorial.libs.aligator.parameters.ServiceResponse;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class RestClientManager {


	public ServiceResponse invoke(RequestContext context) {

		HttpProtocol protocol = context.getProtocol();
		return ServiceResponse.buildResponse(protocol.call(context));

		/*Response response = null;

		if (context.getRequestMethod() == POST)
			response = doPost(context);

		else if (context.getRequestMethod() == GET)
			response = doGet(context);

		else if (context.getRequestMethod() == DELETE)
			response = doDelete(context);

		else if (context.getRequestMethod() == PUT)
			response = doPut(context);

		return response;*/

	}

	/*public Response doGet(RequestContext request) {
		RequestSpecification requestBuilder = getRequestBuilder(request);
		return requestBuilder.log().all()
				.when().get(request.getEndPoint())
				.then().log().all()
				.extract().response();
	}

	public Response doPost(RequestContext request) {
		RequestSpecification requestBuilder = getRequestBuilder(request);
		Response result = requestBuilder.log().all()
				.when().post(request.getEndPoint())
				.then().log().all()
				.extract().response();
		return result;
	}

	public Response doDelete(RequestContext request) {
		RequestSpecification requestBuilder = getRequestBuilder(request);
		return requestBuilder.delete(request.getEndPoint()).thenReturn();
	}

	public Response doPut(RequestContext request) {
		RequestSpecification requestBuilder = getRequestBuilder(request);
		return requestBuilder.put(request.getEndPoint()).thenReturn();
	}*/

	/*private RequestSpecification getRequestBuilder(RequestContext request){
		RequestSpecification requestBuilder = given();
		requestBuilder.port(WebserverHelper.getPort());
		requestBuilder.contentType(ContentType.JSON);
		String contextPath = WebserverHelper.getContextPath();
		requestBuilder.basePath(contextPath);
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
	}*/
}

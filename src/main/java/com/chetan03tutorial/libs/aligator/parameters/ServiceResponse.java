package com.chetan03tutorial.libs.aligator.parameters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Getter
@Setter
@Builder
@JsonIgnoreProperties({"headers","responseBody"})
public class ServiceResponse {

	private String statusCode;
	private String responseBody;
	private Map<String,String> headers;
	private String contentType;
	private String statusLine;
	private String sessionId;
	private String duration;
	private Map<String, String> cookies;


	public static ServiceResponse buildResponse(Response response){
		return ServiceResponse.builder()
				.contentType(response.getContentType())
				.responseBody(response.body().asString())
				.statusCode(String.valueOf(response.getStatusCode()))
				.headers(response.getHeaders().asList().stream()
						.collect(toMap(Header::getName, Header::getValue)))
				.statusLine(response.getStatusLine())
				.sessionId(response.getSessionId())
				.duration(String.valueOf(response.getTime()))
				.cookies(response.getCookies())
				.build();
	}
}

package com.chetan03tutorial.libs.aligator.parameters;

import com.chetan03tutorial.libs.aligator.exception.AligatorBddException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.chetan03tutorial.libs.aligator.client.rest.HttpProtocol;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
@JsonIgnoreProperties({"endPoint", "cookies" ,"requestMethod", "protocol"})
public class RequestContext {

	private String serviceName;
	private String endPoint;
	private String requestBody;
	private HttpProtocol protocol;
	private Map<String, String> cookies;

	private ConcurrentHashMap<String, String> headers;
	private ConcurrentHashMap<String, String> pathParams;
	private ConcurrentHashMap<String, String> formParams;
	private ConcurrentHashMap<String, Object> queryParams;
	private ConcurrentHashMap<String, File> multipartParams;
	private final String ERROR_MSG = "Error while adding the %s \" %s \" = \" %s \"";

	public RequestContext() {
		this.pathParams = new ConcurrentHashMap<>();
		this.headers = new ConcurrentHashMap<>();
		this.formParams = new ConcurrentHashMap<>();
		this.queryParams = new ConcurrentHashMap<>();
		this.cookies = new HashMap<>();
		this.multipartParams = new ConcurrentHashMap<>();
	}

	public RequestContext addPathParam(String paramName, String paramValue) {
		try {
			pathParams.putIfAbsent(paramName, paramValue);
		} catch (Exception ex) {
			throw new AligatorBddException(String.format(ERROR_MSG, "Path-Param", paramName, paramValue));
		}
		return this;
	}

	public RequestContext addFormParam(String paramName, String paramValue) {
		try {
			formParams.putIfAbsent(paramName, paramValue);
		} catch (Exception ex) {
			throw new AligatorBddException(String.format(ERROR_MSG, "Form-Param", paramName, paramValue));
		}
		return this;
	}

	public RequestContext addQueryParam(String paramName, Object paramValue) {
		try {
			queryParams.put(paramName, paramValue);
		} catch (Exception ex) {
			throw new AligatorBddException(String.format(ERROR_MSG, "Query-Param", paramName, paramValue));
		}
		return this;
	}

	public RequestContext addMultipartParam(String paramName, File paramValue) {
		try {
			multipartParams.putIfAbsent(paramName, paramValue);
		} catch (Exception ex) {
			throw new AligatorBddException(String.format(ERROR_MSG, "Multipart-Param", paramName, paramValue));
		}
		return this;
	}

	public RequestContext addHeaderParam(String paramName, String paramValue) {
		try {
			headers.putIfAbsent(paramName, paramValue);
		} catch (Exception ex) {
			throw new AligatorBddException(String.format(ERROR_MSG,"Header", paramName, paramValue));
		}
		return this;
	}



}

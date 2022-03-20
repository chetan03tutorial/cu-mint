package com.chetan03tutorial.libs.aligator.step;

import com.chetan03tutorial.libs.aligator.client.rest.RestClientManager;
import com.chetan03tutorial.libs.aligator.constant.AligatorGrammar;
import com.chetan03tutorial.libs.aligator.constant.RestProtocols;
import com.chetan03tutorial.libs.aligator.parameters.RequestContext;
import com.chetan03tutorial.libs.aligator.parameters.ServiceResponse;
import com.chetan03tutorial.libs.aligator.parameters.RequestContextBuilder;
import com.chetan03tutorial.libs.aligator.util.Cache;
import com.chetan03tutorial.libs.aligator.util.EndpointService;
import com.chetan03tutorial.libs.aligator.util.FileReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.junit.Assert.assertEquals;


public class RestInvocationSteps implements En {

    private final RestClientManager clientManager;
    private final RequestContextBuilder contextBuilder;
    private final Cache cache;

    public RestInvocationSteps(RestClientManager clientManager,
                               RequestContextBuilder contextBuilder,
                               Cache cache){
        System.out.println("Came Inside Constructor");
        this.clientManager = clientManager;
        this.contextBuilder = contextBuilder;
        this.cache = cache;
        init();
    }

    private String grammar(AligatorGrammar sentence){
        return sentence.toString();
    }

    private void init(){
        When(grammar(AligatorGrammar.SERVICE_INVOCATION), this::invoke);
        Then(grammar(AligatorGrammar.STATUS_COMPARISON), this::expectedStatus);
        Then(grammar(AligatorGrammar.RESPONSE_COMPARISON), this::compareJsonLeniently);
        Then(grammar(AligatorGrammar.STRICT_RESPONSE_COMPARISON), this::compareJsonStrictly);
    }

    public void invoke(String serviceName, DataTable dataTable){
        //TODO , Make sure that serviceName is not null.
        RequestContext context = contextBuilder.buildContext(dataTable);
        String endpoint = EndpointService.getEndpoint(serviceName);
        String operation = EndpointService.getOperation(serviceName);
        context.setEndPoint(endpoint);
        context.setProtocol(RestProtocols.protocol(operation));
        ServiceResponse response = clientManager.invoke(context);
        cache.save(serviceName,response);
    }

    /*public void invoke(DataTable dataTable){
        //TODO , Make sure that tag corresponding to serviceName and its value are coming in dataTable. Else throw exception
        RequestContext context = contextBuilder.buildContext(dataTable);
        ServiceResponse response = clientManager.invoke(context);
        String serviceName = context.getServiceName();
        cache.save(serviceName,response);
    }*/

    public void expectedStatus(String expectedValue){
        assertEquals(expectedValue,String.valueOf(cache.getResponse().getStatusCode()));
    }

    public void compareJsonLeniently(String name) throws JSONException {
        doCompareJson(name,false);
    }

    public void compareJsonStrictly(String name) throws JSONException {
        doCompareJson(name,true);
    }

    private void doCompareJson(String name, boolean isStrictMode) throws JSONException {
        String expectedJson = FileReader.readFile(name);
        JSONAssert.assertEquals(expectedJson, cache.getResponse().getResponseBody(),
                isStrictMode?JSONCompareMode.STRICT:JSONCompareMode.LENIENT);
    }

}

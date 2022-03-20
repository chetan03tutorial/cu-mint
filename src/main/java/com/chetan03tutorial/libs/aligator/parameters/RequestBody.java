package com.chetan03tutorial.libs.aligator.parameters;

import static com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics.REQUEST_BODY;

import com.chetan03tutorial.libs.aligator.parsers.CacheExpressionEvaluator;
import com.chetan03tutorial.libs.aligator.parsers.LiteralParameterParser;
import com.chetan03tutorial.libs.aligator.util.FileReader;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import org.springframework.stereotype.Component;
import static java.lang.String.*;
import java.security.InvalidParameterException;
import java.util.List;

@Component
public class RequestBody implements AligatorParameter {

	private final LiteralParameterParser parser;
	private final CacheExpressionEvaluator evaluator;

	public RequestBody(LiteralParameterParser parser,CacheExpressionEvaluator evaluator){
		this.parser = parser;
		this.evaluator = evaluator;
	}

	public void prepareContext(RequestContext request, String cellText) {
		/*List<Pair> params = parser.parse(cellText);
		if(params.size() !=1 ){
			throw new InvalidParameterException(
					format("Invalid number of arguments provided for type %s, Maximum allowed argument is 1", getMnemonic()));
		}
		String fileName = params.get(0).getParamValue();
		String requestBody = FileReader.readFile(cacheExpressionEvaluator.evaluate(fileName));
		request.setRequestBody(requestBody);*/


		/*List<Pair> parameters = cacheExpressionEvaluator.evaluateExpression(parser.parseCell(cellText));*/
		List<Pair> parameters = parser.parse(cellText);
		if(parameters.size() !=1 ){
			throw new InvalidParameterException(
					format("Invalid number of arguments provided for type %s, Maximum allowed argument is 1", getMnemonic()));
		}
		String fileName = parameters.get(0).value;
		// TODO :- Do this entire operation on a string buffer so that we manipulate the same string instead of creating
		// new String objects for every pattern matching.
		String requestBody = FileReader.readFile(fileName);
		do{
			String expression = evaluator.extractExpression(requestBody);
			if(expression == null){
				break;
			}
			requestBody = requestBody.replace(expression, evaluator.evaluate(expression));
		}while(true);
		request.setRequestBody(requestBody);
	}

	@Override
	public String getMnemonic() {
		return PropertyResolver.getApplicationProperty(REQUEST_BODY);
	}
}

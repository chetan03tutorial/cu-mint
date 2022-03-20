package com.chetan03tutorial.libs.aligator.parameters;

import static com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics.QUERY_PARAM;

import com.chetan03tutorial.libs.aligator.parsers.KVParameterParser;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryParameter implements AligatorParameter {

	private final KVParameterParser kvParameterParser;

	public QueryParameter(KVParameterParser parser){
		this.kvParameterParser = parser;
	}

	public String getMnemonic() {
		return PropertyResolver.getApplicationProperty(QUERY_PARAM);
	}

	public void prepareContext(RequestContext request, String cellText) {
		/*List<Pair> parameters = cacheExpressionEvaluator.evaluateExpression(kvParameterParser.parseCell(cellText));*/
		List<Pair> parameters = kvParameterParser.parse(cellText);
		parameters.stream().forEach(p -> request.addQueryParam(p.name,p.value));
	}
}
package com.chetan03tutorial.libs.aligator.parameters;

import com.chetan03tutorial.libs.aligator.parsers.CacheExpressionEvaluator;
import com.chetan03tutorial.libs.aligator.parsers.KVParameterParser;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics.HEADER_PARAM;

@Component
public class HeaderParameter implements AligatorParameter {

	private final KVParameterParser kvParameterParser;

	public HeaderParameter(KVParameterParser parser, CacheExpressionEvaluator evaluator){
		this.kvParameterParser = parser;
	}

	public void prepareContext(RequestContext request, String cellText) {
		List<Pair> parameters = kvParameterParser.parse(cellText);
		parameters.stream().forEach(p -> request.addHeaderParam(p.name,p.value));
	}

	public String getMnemonic() {
		return PropertyResolver.getApplicationProperty(HEADER_PARAM);
	}

}

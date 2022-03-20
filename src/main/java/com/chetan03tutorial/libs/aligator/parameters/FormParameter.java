package com.chetan03tutorial.libs.aligator.parameters;

import static com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics.FORM_PARAM;

import com.chetan03tutorial.libs.aligator.parsers.KVParameterParser;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class FormParameter implements AligatorParameter {

	private final KVParameterParser kvParameterParser;

	public FormParameter(KVParameterParser parser){
		this.kvParameterParser = parser;
	}

	public String getMnemonic() {
		return PropertyResolver.getApplicationProperty(FORM_PARAM);
	}

	public void prepareContext(RequestContext request, String cellText) {
		List<Pair> parameters = kvParameterParser.parse(cellText);
		parameters.stream().forEach(p -> request.addFormParam(p.name,p.value));
	}

}

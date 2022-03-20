package com.chetan03tutorial.libs.aligator.parameters;

import com.chetan03tutorial.libs.aligator.exception.GherkinParameterFormatException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.Getter;
import static com.chetan03tutorial.libs.aligator.constant.AligatorErrorMessages.INVALID_PARAMETER_FORMAT;

import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class RequestContextBuilder {

	private static final String OPEN_BRACE = "(";
	private static final String CLOSE_BRACE = ")";
	private static final String PARAMETER_PATTERN = "(?<=\\@)([a-zA-Z_]+)(?=\\()";

	public RequestContext buildContext(DataTable table){
		RequestContext context = new RequestContext();
		List<Parameter> parameters = parseDatatable(table);
		parameters.forEach(p -> AligatorParameterHandler.handle(p.type,p.text,context));
		return context;
	}

	private List<Parameter> parseDatatable(DataTable table){
		return table.row(0).stream().map(this::parseCell).collect(Collectors.toList());
	}

	private Parameter parseCell(String text){
		Matcher matcher = Pattern.compile(PARAMETER_PATTERN).matcher(text);
		if(! matcher.find()){
			throw new GherkinParameterFormatException(String.format(INVALID_PARAMETER_FORMAT,text));
		}
		String paramType = matcher.group();
		String paramText = StringUtils.substringBetween(text,OPEN_BRACE,CLOSE_BRACE);
		return new Parameter(paramType, paramText);
	}

	@Getter
	@AllArgsConstructor
	private class Parameter {
		private String type;
		private String text;
	}
}

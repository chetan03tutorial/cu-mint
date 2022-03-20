package com.chetan03tutorial.libs.aligator.parameters;

import static com.chetan03tutorial.libs.aligator.constant.GherkinParameterMnemonics.PATH_PARAM;

import com.chetan03tutorial.libs.aligator.parsers.KVParameterParser;
import com.chetan03tutorial.libs.aligator.util.PropertyResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PathParameter implements AligatorParameter {

    private final KVParameterParser kvParameterParser;

    public PathParameter(KVParameterParser parser){
        this.kvParameterParser = parser;
    }

    @Override
    public String getMnemonic() {
        return PropertyResolver.getApplicationProperty(PATH_PARAM);
    }


    public void prepareContext(RequestContext request, String cellText) {
        List<Pair> parameters = kvParameterParser.parse(cellText);
        parameters.stream().forEach(p -> request.addPathParam(p.name,p.value));
    }

}

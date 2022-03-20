package com.chetan03tutorial.libs.aligator.parsers;

import com.chetan03tutorial.libs.aligator.parameters.Pair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.stream.Collectors;

@Component
public class LiteralParameterParser {

    private final CacheExpressionEvaluator evaluator;

    public LiteralParameterParser(CacheExpressionEvaluator evaluator){
        this.evaluator = evaluator;
    }

    public List<Pair> parse(String cellText){
        List<Pair> parameters;
        //String parameter = cellText.replaceAll("\\s","");
        if(cellText.contains(",")){
            String[] params = cellText.split(",");
            parameters = Arrays.stream(params).map(this::parseExpression).collect(Collectors.toList());
        }else {
            parameters = Collections.singletonList(parseExpression(cellText));
        }
        return parameters;
    }

    /*public Map<String,String> parseCell(String cellText){
        Map<String,String> params = new HashMap<>();
        String parameters = cellText;
        if(parameters.contains(",")){
            String[] literals = parameters.split(",");
            Arrays.stream(literals).forEach(s -> params.put(s,s));
        }else {
            params.putIfAbsent(parameters,parameters);
        }
        return params;
    }*/

    private Pair parseExpression(String param){
        String value = evaluator.evaluate(StringUtils.trim(param));
        Pair pair = new Pair();
        pair.name = value;
        pair.value = value;
        return pair;
    }
}

package com.chetan03tutorial.libs.aligator.parsers;

import com.chetan03tutorial.libs.aligator.exception.InvalidExpressionException;
import com.chetan03tutorial.libs.aligator.parameters.ArrayToken;
import com.chetan03tutorial.libs.aligator.parameters.ObjectToken;
import com.chetan03tutorial.libs.aligator.parameters.Token;
import com.chetan03tutorial.libs.aligator.util.Cache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class CacheExpressionEvaluator {

    private final Cache cache;
    private final static String EXPRESSION_REGEX = "(\\$\\{)(.*?)(})";
    private final static Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_REGEX);
    private final static String CACHED_PARAMETER_REGEX = "(?<=\\$\\{)(.*?)(?=})";
    private final static Pattern CACHED_PARAMETER_PATTERN = Pattern.compile(CACHED_PARAMETER_REGEX);
    private final static String ARRAY_PATTERN = "^(.*?\\[)([0-9]+)(])";
    private final static String ARRAY_OPEN = "[";
    private final static String ARRAY_CLOSE = "]";



    public CacheExpressionEvaluator(Cache cache){
        this.cache = cache;
    }

    /*public List<Pair> evaluateExpression(Map<String,String> parameters){
        return parameters.entrySet()
                .stream()
                .map(this::createGherkinParameter)
                .collect(toList());
    }*/


    /*private Pair createGherkinParameter(Map.Entry<String,String> e){
        Pair parameter = new Pair();
        parameter.setParamName(evaluate(e.getKey()));
        parameter.setParamValue(evaluate(e.getValue()));
        return parameter;
    }*/

    /*private Pair parseParameter(String name, String text){
        Pair parameter = new Pair();
        parameter.setParamName(evaluate(name));
        parameter.setParamValue(evaluate(text));
        return parameter;
    }*/

    public String extractExpression(String content){
        Matcher m = EXPRESSION_PATTERN.matcher(content);
        if(m.find()){
            return m.group();
        }
        return null;
    }

    public String evaluate(String parameter){
        Matcher m = CACHED_PARAMETER_PATTERN.matcher(parameter);
        if(m.find()){
            AlCacheExpression exp = this.buildGherkinContext(m.group());
            return Optional.ofNullable(cache.get(exp.service, exp.index, exp.jsonPath))
                    .orElseThrow(() -> new InvalidExpressionException(String.format("Invalid Expression :- %s", parameter)));
        }else{
            return parameter;
        }
    }


    private AlCacheExpression buildGherkinContext(String value){
        String cellText = value.replaceAll("\\s", "");
        LinkedList<Token> tokens = tokenizeExpression(cellText);
        Token serviceToken = Optional.ofNullable(tokens.poll())
                .orElseThrow(() -> new InvalidExpressionException(String.format("Invalid Expression :- %s", cellText)));
        int invocationIndex = 0;
        if(serviceToken instanceof ArrayToken){
            invocationIndex = ((ArrayToken)serviceToken).getIndex();
        }
        String jsonPath = tokens.stream()
                .map(this::buildJsonpath)
                .collect(Collectors.joining("/"));
        String jsonPointer = "/".concat(jsonPath);
        return AlCacheExpression.builder()
                .service(serviceToken.getName())
                .index(invocationIndex)
                .jsonPath(jsonPointer).build();
    }


    private String buildJsonpath(Token token){
        String pathXpression = "";
        if(token instanceof ArrayToken){
            ArrayToken at = (ArrayToken)token;
            pathXpression = pathXpression.concat(at.getName()).concat("/").concat(String.valueOf(at.getIndex()));
        }
        if(token instanceof ObjectToken){
            ObjectToken ot = (ObjectToken) token;
            pathXpression = pathXpression.concat(ot.getName());
        }
        return pathXpression;
    }

    private LinkedList<Token> tokenizeExpression(String exp){

        LinkedList<Token> tokenList = new LinkedList<>();
        List<String> tokens = Arrays.stream(exp.split("\\.")).collect(Collectors.toList());
        if(tokens.size() < 1){
            throw new InvalidExpressionException(String.format("Invalid Expression :- %s", exp));
        }
        tokens.stream().map(this::createToken).forEach(tokenList::addLast);
        return tokenList;
    }

    private Token createToken(String literal){
        int index;
        Token token;
        if(Pattern.compile(ARRAY_PATTERN).matcher(literal).find()){
            String indexStr = StringUtils.substringBetween(literal,ARRAY_OPEN,ARRAY_CLOSE);
            try{
                index = Integer.parseInt(indexStr);
            }catch (NumberFormatException ex){
                throw new InvalidExpressionException(String.format("Invalid Index value for the token %s of type Array", literal));
            }
            String field = literal.substring(0,literal.indexOf('['));
            token = new ArrayToken(field,index);
        }else {
            // The remaining value should contain only a-zA-Z0-0_-,
            // failure to fulfill the pattern would lead to invalid expression exception
            token = new ObjectToken(literal);
        }
        return token;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class AlCacheExpression {
        private final String service;
        private final int index;
        private final String jsonPath;
    }
}

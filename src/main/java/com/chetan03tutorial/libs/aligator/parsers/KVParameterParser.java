package com.chetan03tutorial.libs.aligator.parsers;

import com.chetan03tutorial.libs.aligator.parameters.Pair;

import static org.apache.commons.lang3.StringUtils.trim;
import org.springframework.stereotype.Component;

import java.util.*;
import static java.util.stream.Collectors.*;

@Component
public class KVParameterParser {


    private final CacheExpressionEvaluator evaluator;

    public KVParameterParser(CacheExpressionEvaluator evaluator){
        this.evaluator = evaluator;
    }

    /*public List<Pair> parse(String cellText){
        cellText = cellText.replaceAll("\\s", "");
        if(cellText.contains(",")){
            String[] params = cellText.split(",");
            return Arrays.stream(params)
                    .filter(p -> p.contains("="))
                    .map(p -> p.split("="))
                    .map(kv -> new Pair(kv[0].trim(),kv[1].trim()))
                    .collect(Collectors.toList());
        }else if(cellText.contains("=")){
            String[] params = cellText.split("=");
            return Arrays.asList(new Pair(params[0].trim(),params[1].trim()));
        }
        return new ArrayList<>();
    }*/

    /*public Map<String,String> parseCell(String cellText){
        Map<String,String> paramMap = new HashMap<>();
        cellText = cellText.replaceAll("\\s", "");
        if(cellText.contains(",")){
            String[] params = cellText.split(",");
            Arrays.stream(params)
                    .filter(p -> p.contains("="))
                    .map(p -> p.split("="))
                    .forEach(kv -> paramMap.put(kv[0],kv[1]));
        }else if(cellText.contains("=")){
            String[] params = cellText.split("=");
            paramMap.put(params[0],params[1]);
        }
        return paramMap;
    }*/

    public List<Pair> parse(String cellText){
        List<Pair> parameters = new ArrayList<>();
        //cellText = cellText.replaceAll("\\s", "");
        if(cellText.contains(",")){
            String[] params = cellText.split(",");
            parameters = Arrays.stream(params)
                    .filter(p -> p.contains("="))
                    .map(p -> p.split("="))
                    .map(this::parseExpression)
                    .collect(toList());
        }else if(cellText.contains("=")){
            parameters = Collections.singletonList(this.parseExpression(cellText.split("=")));
        }
        return parameters;
    }

    private Pair parseExpression(String[] params){
        Pair pair = new Pair();
        pair.name = evaluator.evaluate(trim(params[0]));
        pair.value = evaluator.evaluate(trim(params[1]));
        return pair;
    }
}

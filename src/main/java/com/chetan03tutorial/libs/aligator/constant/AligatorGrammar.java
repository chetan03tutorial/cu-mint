package com.chetan03tutorial.libs.aligator.constant;

import com.chetan03tutorial.libs.aligator.util.PropertyResolver;

public enum AligatorGrammar {
    SERVICE_INVOCATION("alligator.steps.grammar.service-invocation"),
    STATUS_COMPARISON("alligator.steps.grammar.status-comparison"),
    RESPONSE_COMPARISON ("alligator.steps.grammar.lenient-response-comparison"),
    STRICT_RESPONSE_COMPARISON ("alligator.steps.grammar.strict-response-comparison");

    private String key;

    AligatorGrammar(String property){
        this.key = property;
    }

    @Override
    public String toString(){
        System.out.println(String.format("Value of the property %s is %s", this.key, PropertyResolver.getApplicationProperty(this.key)));
        return PropertyResolver.getApplicationProperty(this.key);
    }
}

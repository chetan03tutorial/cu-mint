package com.chetan03tutorial.libs.aligator.parser;

import com.chetan03tutorial.libs.aligator.parsers.CacheExpressionEvaluator;
import com.chetan03tutorial.libs.aligator.util.Cache;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ExpressionParserTest {

    private CacheExpressionEvaluator cacheExpressionEvaluator;
    private Cache cache;

    @BeforeEach
    public void setup(){
        cache = mock(Cache.class);
        cacheExpressionEvaluator = new CacheExpressionEvaluator(cache);
    }

    @Test
    public void testParsingOfSimpleKey(){
        String expected = "TEST";
        String actual = cacheExpressionEvaluator.evaluate(expected);
        assertEquals(actual,expected);
    }

    /*@Test
    public void testParsingOfExpression(){
        String expression = "${order[0].response.orderData}";
        String actual = cacheExpressionEvaluator.evaluate(expression);
        verify(cacheExpressionParser,times(1)).buildGherkinContext("order[0].response.orderData");
        verify(cache, times(1)).get("order", 0, "response/orderData");
        //when(cache.get("order", 0, "/response/orderData")).thenReturn("TEST-VALUE");
        //assertEquals(actual,"TEST-VALUE");
        *//*Assert.assertEquals(actual.getIndex(),0);
        Assert.assertEquals(actual.getService(),"order");
        Assert.assertEquals(actual.getJsonPath(),"response/orderData");*//*
    }*/
}

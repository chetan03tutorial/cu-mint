package com.chetan03tutorial.libs.aligator;

import com.chetan03tutorial.libs.aligator.util.FileReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestBodyTester {
    private final static String CACHED_PARAMETER_REGEX = "(\\$\\{)(.*?)(})";
    private final static Pattern CACHED_PARAMETER_PATTERN = Pattern.compile(CACHED_PARAMETER_REGEX);

    public static void main(String[] args) {
        String content = FileReader.readFile("order.json");
        //Matcher m = CACHED_PARAMETER_PATTERN.matcher(sb);
        do{
            Matcher m = CACHED_PARAMETER_PATTERN.matcher(content);
            if(! m.find()) {
                break;
            }
            //System.out.println("Number of occurrence is " + m.groupCount());
            String s = m.group();
            //System.out.println("Index of $ is " + content.indexOf("$"));
            System.out.println("Pattern Found is " + s);
            /*System.out.println("Index is " + m.start());*/
            content = content.replace(s,"Hello World");
            System.out.println(" ============ After replacing the content is ========== " + content);
            /*m = CACHED_PARAMETER_PATTERN.matcher(content);*/
            //System.out.println("After replacing the content is " + content);
        }while(true);

    }
}

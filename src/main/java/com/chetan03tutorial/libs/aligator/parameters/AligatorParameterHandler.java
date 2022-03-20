package com.chetan03tutorial.libs.aligator.parameters;

import com.chetan03tutorial.libs.aligator.constant.AligatorErrorMessages;
import com.chetan03tutorial.libs.aligator.exception.AligatorBddException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class AligatorParameterHandler {

    private static Map<String, AligatorParameter> handlers;

    public static void handle(String mnemonic, String text, RequestContext context){
        Optional.ofNullable(handlers.get(mnemonic))
                .orElseThrow(()-> new AligatorBddException(String.format(AligatorErrorMessages.INVALID_PARAM_TYPE,mnemonic)));
        AligatorParameter handler = handlers.get(mnemonic);
        handler.prepareContext(context,text);
    }

     @EventListener
    public void onContextRefreshed(ContextRefreshedEvent event) {
        handlers = event.getApplicationContext()
                .getBeansOfType(AligatorParameter.class)
                .values().stream()
                .collect(toMap(AligatorParameter::getMnemonic, Function.identity()));
    }

}

package com.chetan03tutorial.libs.aligator.parameters;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArrayToken extends ObjectToken{

    private int index;
    public ArrayToken(String name, int index){
        super(name);
        this.index = index;
    }

}

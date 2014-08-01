package com.example;

public class BusinessClass {

    public String businessMethod(String param) {
        System.out.println("param = " + param);
        final String split = param.split(" ")[0];
        System.out.println("after split = " + split);
        return split;
    }

}

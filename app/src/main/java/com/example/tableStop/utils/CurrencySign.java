package com.example.tableStop.utils;

import java.util.Objects;

public class CurrencySign {
    public static String convertTextToSign(String currency){
        if (Objects.equals(currency, "USD")) {
            return "$";
        } else return "￥";
    }
}
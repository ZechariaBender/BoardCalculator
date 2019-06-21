package com.zechariabender.boardcalculator;

import java.util.Random;

public class BooleanOperatorFactory {

    // number of implementations of BooleanOperator interface
    private static final int NUM_OF_OPERATORS = 4;

    public static BooleanOperator generateRandomOperator() {
        Random random = new Random();
        int result = random.nextInt(NUM_OF_OPERATORS);
        switch (result) {
            case 1: return new TRUEOperator();
            case 2: return new ANDOperator();
            case 3: return new OROperator();
            default: return new FALSEOperator(); // default covers case: 0
        }
    }

    public static BooleanOperator generateOperator(String operator) {
        switch (operator) {
            case "TRUE": return new TRUEOperator();
            case "AND": return new ANDOperator();
            case "OR": return new OROperator();
            default: return new FALSEOperator(); // default covers case: "FALSE"
        }
    }

}

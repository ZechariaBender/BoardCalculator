package com.zechariabender.boardcalculator;

import java.util.Random;

public class BooleanOperatorFactory {

    // number of implementations of BooleanOperator interface
    private static final int NUM_OF_OPERATORS = 6;

    public static BooleanOperator generateRandomOperator() {
        Random random = new Random();
        int result = random.nextInt(NUM_OF_OPERATORS);
        switch (result) {
            case 1: return new TRUEOperator();
            case 2: return new ANDOperator();
            case 3: return new OROperator();
            case 4: return new NANDOperator();
            case 5: return new XOROperator();
            default: return new FALSEOperator(); // default covers case: 0
        }
    }

    public static BooleanOperator generateOperator(String operator) {
        switch (operator) {
            case "TRUE": return new TRUEOperator();
            case "AND": return new ANDOperator();
            case "OR": return new OROperator();
            case "NAND": return new ANDOperator();
            case "XOR": return new OROperator();
            default: return new FALSEOperator(); // default covers case: "FALSE"
        }
    }

}

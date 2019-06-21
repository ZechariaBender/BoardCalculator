package com.zechariabender.boardcalculator;

public class FALSEOperator implements BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return false;
    }
}

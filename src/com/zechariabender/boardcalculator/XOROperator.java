package com.zechariabender.boardcalculator;

public class XOROperator implements BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return a ^ b;
    }
}

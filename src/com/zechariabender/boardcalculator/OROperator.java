package com.zechariabender.boardcalculator;

public class OROperator implements BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return a || b;
    }
}

package com.zechariabender.boardcalculator.operator;

public class OROperator extends BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return a || b;
    }
}

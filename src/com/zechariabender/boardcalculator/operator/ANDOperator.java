package com.zechariabender.boardcalculator.operator;

public class ANDOperator extends BooleanOperator {

    @Override
    public boolean calculate(boolean a, boolean b) {
        return a && b;
    }
}

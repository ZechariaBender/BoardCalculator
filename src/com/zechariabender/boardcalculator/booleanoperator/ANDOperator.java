package com.zechariabender.boardcalculator.booleanoperator;

public class ANDOperator extends BooleanOperator {

    @Override
    public boolean calculate(boolean a, boolean b) {
        return a && b;
    }
}

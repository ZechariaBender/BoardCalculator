package com.zechariabender.boardcalculator.operator;

public class NANDOperator extends BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return !(a && b);
    }
}

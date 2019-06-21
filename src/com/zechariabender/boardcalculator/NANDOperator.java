package com.zechariabender.boardcalculator;

public class NANDOperator implements BooleanOperator {
    @Override
    public boolean calculate(boolean a, boolean b) {
        return !(a && b);
    }
}

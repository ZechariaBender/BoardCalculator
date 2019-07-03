package com.zechariabender.boardcalculator.operator;

import java.io.Serializable;

public abstract class BooleanOperator implements Serializable {
    public abstract boolean calculate(boolean a, boolean b);
}

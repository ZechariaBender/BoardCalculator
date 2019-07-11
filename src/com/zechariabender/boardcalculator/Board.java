package com.zechariabender.boardcalculator;

public interface Board {

    boolean setInputs(boolean[] input);
    boolean calculateInput();
    boolean concurrentCalculateInput();
    String serialize();
    int getExponent();
    int getRootOperator();
}

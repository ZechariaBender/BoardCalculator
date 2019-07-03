package com.zechariabender.boardcalculator;

public interface Board {
    int getExponent();
    boolean setInputs(boolean[] input);
    boolean calculateInput();
}

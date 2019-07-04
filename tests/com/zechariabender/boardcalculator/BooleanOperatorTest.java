package com.zechariabender.boardcalculator;

import com.zechariabender.boardcalculator.booleanoperator.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class BooleanOperatorTest {

    private static BooleanOperator operator;

    @Test
    public void FASLEOperatorTest() {
        operator = new FALSEOperator();
        assertFalse(operator.calculate(true,true));
        assertFalse(operator.calculate(true,false));
        assertFalse(operator.calculate(false,true));
        assertFalse(operator.calculate(false,false));
    }

    @Test
    public void TRUEOperatorTest() {
        operator = new TRUEOperator();
        assertTrue(operator.calculate(true,true));
        assertTrue(operator.calculate(true,false));
        assertTrue(operator.calculate(false,true));
        assertTrue(operator.calculate(false,false));
    }

    @Test
    public void ANDOperatorTest() {
        operator = new ANDOperator();
        assertTrue(operator.calculate(true,true));
        assertFalse(operator.calculate(true,false));
        assertFalse(operator.calculate(false,true));
        assertFalse(operator.calculate(false,false));
    }

    @Test
    public void OROperatorTest() {
        operator = new OROperator();
        assertTrue(operator.calculate(true,true));
        assertTrue(operator.calculate(true,false));
        assertTrue(operator.calculate(false,true));
        assertFalse(operator.calculate(false,false));
    }

    @Test
    public void NANDOperatorTest() {
        operator = new NANDOperator();
        assertFalse(operator.calculate(true,true));
        assertTrue(operator.calculate(true,false));
        assertTrue(operator.calculate(false,true));
        assertTrue(operator.calculate(false,false));
    }

    @Test
    public void XOROperatorTest() {
        operator = new XOROperator();
        assertFalse(operator.calculate(true,true));
        assertTrue(operator.calculate(true,false));
        assertTrue(operator.calculate(false,true));
        assertFalse(operator.calculate(false,false));
    }
}

package com.zechariabender.boardcalculator;

import org.junit.Test;

import static com.zechariabender.boardcalculator.BoardCalculator.BoardType.*;
import static org.junit.Assert.*;

// tests that the boards calculate correctly
// only tests boards with exponent = 1 (2^1 = 2 inputs)
public class BoardCalculationTest {

    private static Board board;

    @Test
    // tests both board types
    public void calculationTest() {
        for (int i = 0; i < 100; i++) // with 100 runs its almost
            runTest(ENCODED_OPERATOR);// certain that all operators
        for (int i = 0; i < 100; i++) // were tested
            runTest(OBJECT_OPERATOR);
    }

    // tests one operator with all possible inputs
    private void runTest(BoardCalculator.BoardType boardType) {
        try {
            board = BoardCalculator.logConstructionPerformance(boardType,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 4; i++) // each i holds a set of 2 boolean values
            testInputs(i);          // 0 = 00 = {false, false}
                                    // 1 = 01 = {false, true}
                                    // 2 = 10 = {true, false}
                                    // 3 = 11 = {true, true}
    }

    // tests an operator with a specific input
    private void testInputs(int x) {
        // convert int parameter to set of booleans
        board.setInputs(new boolean[] {(x / 2) > 0, (x % 2) > 0});

        // shift boolean operator bits right x times to get
        // the xth bit from the right
        boolean result = ((board.getRootOperator() >> x) & 1) > 0; // cast to boolean
        assertEquals(result, board.calculateInput());
    }
}

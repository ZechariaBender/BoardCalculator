package com.zechariabender.boardcalculator;

import org.junit.Test;

import static com.zechariabender.boardcalculator.BoardCalculator.BoardType.*;
import static org.junit.Assert.*;

// tests that saving a board to file and reloading
// it again gives the exact same board
public class BoardPersistenceTest {

    @Test
    // tests both board types with every exponent in (0,20).
    // higher exponents can be tested as well (but are much longer)
    public void persistenceTest() {
        for (int i = 0; i < 20; i++) {
            runTest(ENCODED_OPERATOR, i);
            runTest(OBJECT_OPERATOR, i);
        }
    }

    // initializes 2 boards, board A constructed from scratch
    // and board B deserialized from the file of board A, and compares them
    private void runTest(BoardCalculator.BoardType boardType, int exponent) {
        BoardProvider provider = new BoardProvider();
        boolean[] input = BoardCalculator.initInput(exponent);
        try {
            Board boardA = BoardCalculator.logConstructionPerformance(boardType,exponent);
            provider.save(boardA, "board.txt");
            Board boardB = provider.load("board.txt");
            // compare the outputs and thereby verify that the
            // calculation logic works properly and is unchanged
            assertEquals(
                    BoardCalculator.calculateBoard(boardA, input),
                    BoardCalculator.calculateBoard(boardB, input));
            // compare the actual content of the board
            assertEquals(boardA.serialize(),boardB.serialize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
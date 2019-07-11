package com.zechariabender.boardcalculator;
import org.junit.Test;

import static com.zechariabender.boardcalculator.BoardCalculator.*;
import static com.zechariabender.boardcalculator.BoardCalculator.BoardType.*;

public class BoardPerformanceTest {

    private static boolean[] input;
    private static Board board;

    private void init(int n) {
        input = initInput(n);
        board = logConstructionPerformance(ENCODED_OPERATOR, n);
    }

    @Test
    public void compare() {
        try {
            for (int i = 0; i < 16; i++) {
                init(i);
                logConcurrentCalculationPerformance(board, input);
                logCalculationPerformance(board, input);
                System.out.println("===========================================================");
            }
        } catch (OutOfMemoryError e) {
            if (board.getExponent() > 30)
                System.out.println("Exponent exponent is too large" +
                        "\nPick a value smaller than 31" +
                        "\n(max value of int = 2^31 - 1)");
            else System.out.println("Error: system out of memory");
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

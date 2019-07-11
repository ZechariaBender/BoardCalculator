package com.zechariabender.boardcalculator;
import org.junit.Test;

import static com.zechariabender.boardcalculator.BoardCalculator.*;
import static com.zechariabender.boardcalculator.BoardCalculator.BoardType.*;

public class BoardPerformanceTest {

    @Test
    public void PerformanceTest() {

        int n = 29;

        try {
            logCalculationPerformance(
                    logConstructionPerformance(ENCODED_OPERATOR, n), initInput(n)
            );
        } catch (OutOfMemoryError e) {
            if (n > 30)
                System.out.println("Exponent n is too large" +
                        "\nPick a value smaller than 31" +
                        "\n(max value of int = 2^31 - 1)");
            else System.out.println("Error: system out of memory");
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

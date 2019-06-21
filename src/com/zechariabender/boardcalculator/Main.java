package com.zechariabender.boardcalculator;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int n = 23;
        try {
            boolean[] input = new boolean[(int) Math.pow(2, n)];
            Random random = new Random();
            for (int i = 0; i < input.length; i++)
                input[i] = random.nextBoolean();

            BoardCalculator boardCalculator = new BoardCalculator();
            if (boardCalculator.setInputs(input, n)) {
                boolean result = boardCalculator.calculate();
                System.out.println(result);
            } else System.out.println("Error: length of input array not a power of 2");
        } catch (OutOfMemoryError e) {
            if (n > 30)
                System.out.println("Exponent n is too large" +
                        "\nPick a value smaller than 31" +
                        "\n(max value of int = 2^31 - 1)");
            else System.out.println("Error: system out of memory");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

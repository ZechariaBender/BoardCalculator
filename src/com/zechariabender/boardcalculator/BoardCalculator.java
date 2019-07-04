package com.zechariabender.boardcalculator;

import java.util.Random;

import static com.zechariabender.boardcalculator.BoardCalculator.BoardType.*;

public class BoardCalculator {

    // 2 Board implementations available:
    // EncodedOperatorBoard - lightweight efficient and fast
    // ObjectOperatorBoard - slower but more object-oriented
    public enum BoardType {ENCODED_OPERATOR, OBJECT_OPERATOR}
    private static BoardProvider provider = new BoardProvider();

    public static void main(String[] args) {

        // choose arbitrary n as exponent of 2 (0 < n < 31)
        int n = 10;

        try {
            boolean[] input = initInput(n);
            Board board = constructBoard(ENCODED_OPERATOR, n);
            System.out.println(calculateBoard(board, input));
            provider.save(board,"boardA.txt");
            board = provider.load("boardA.txt");
            System.out.println(calculateBoard(board, input));
            board = constructBoard(OBJECT_OPERATOR, n);
            System.out.println(calculateBoard(board, input));
            provider.save(board,"boardB.txt");
            board = provider.load("boardB.txt");
            System.out.println(calculateBoard(board, input));

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

    static boolean[] initInput(int n) {
        boolean[] input = new boolean[(int) Math.pow(2, n)];
        Random random = new Random();
        for (int i = 0; i < input.length; i++) {
            input[i] = random.nextBoolean();
//            System.out.println(input[i]);
        }

        return input;
    }

    static Board constructBoard(BoardType type, int n) {
        switch (type) {
            case ENCODED_OPERATOR: return new EncodedOperatorBoard(n);
            case OBJECT_OPERATOR: return new ObjectOperatorBoard(n);
            default: return new EncodedOperatorBoard(n);
        }
    }

    static boolean calculateBoard(Board board, boolean[] input) throws Exception {
        if (board.setInputs(input)) {
            return board.calculateInput();
        } else throw new Exception("Error: length of input array not a power of 2");
    }
}

package com.zechariabender.boardcalculator;

import java.util.Random;

public class BoardCalculator {

    // 2 Board implementations available:
    // EncodedOperatorBoard - lightweight, efficient and fast
    // ObjectOperatorBoard - slower but more object-oriented
    public enum BoardType {ENCODED_OPERATOR, OBJECT_OPERATOR}

    public static void main(String[] args) {}

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
            default: return null;
        }
    }

    static Board logConstructionPerformance(BoardType type, int n) {
        Board board = null;
//        double start = System.nanoTime();
        switch (type) {
            case ENCODED_OPERATOR: board = new EncodedOperatorBoard(n);
            break;
            case OBJECT_OPERATOR: board = new ObjectOperatorBoard(n);
        }
//        double runtime = System.nanoTime() - start;
        System.out.println("board depth = " + board.getExponent()
                + " (input size: " + (int) Math.pow(2, board.getExponent()) + ")");
//        System.out.printf("runtime: %.2f seconds (%f ms)\n", runtime / 1_000_000_000, runtime / 1_000_000);
        return board;
    }

    static boolean calculateBoard(Board board, boolean[] input) throws Exception {
        if (board.setInputs(input)) {
            return board.calculateInput();
        } else throw new Exception("Error: length of input array not a power of 2");
    }

    static boolean concurrentCalculateBoard(Board board, boolean[] input) throws Exception {
        if (board.setInputs(input)) {
            return board.concurrentCalculateInput();
        } else throw new Exception("Error: length of input array not a power of 2");
    }

    static void logCalculationPerformance(Board board, boolean[] input) throws Exception {
        double start = System.nanoTime();
        boolean result = calculateBoard(board, input);
        double runtime = System.nanoTime() - start;
//        System.out.println("(Regular) calculated board with depth of " + board.getExponent()
//                + " (input size: " + (int) Math.pow(2, board.getExponent()) + ")");
//        System.out.println("returned result: " + result);
        System.out.printf("REGULAR    runtime: %.2f seconds (%f ms)\n", runtime / 1_000_000_000, runtime / 1_000_000);
    }

    static void logConcurrentCalculationPerformance(Board board, boolean[] input) throws Exception {
        double start = System.nanoTime();
        boolean result = concurrentCalculateBoard(board, input);
        double runtime = System.nanoTime() - start;
//        System.out.println("(concurrent) calculated board with depth of " + board.getExponent()
//                + " (input size: " + (int) Math.pow(2, board.getExponent()) + ")");
//        System.out.println("returned result: " + result);
        System.out.printf("CONCURRENT runtime: %.2f seconds (%f ms)\n", runtime / 1_000_000_000, runtime / 1_000_000);
    }
}

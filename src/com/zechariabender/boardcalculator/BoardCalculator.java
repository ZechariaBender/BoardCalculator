package com.zechariabender.boardcalculator;

import java.util.Random;

public class BoardCalculator {

    private static BoardProvider provider = new BoardProvider();

    public static void main(String[] args) {

        // 0 for EncodedBoard (better performance)
        // 1 for ObjectBoard
        int boardType = 0;

        // choose an arbitrary n as exponent of 2 (0 < n < 31)
        int n = 20;

        try {
            boolean[] input = new boolean[(int) Math.pow(2, n)];
            Random random = new Random();
            for (int i = 1; i < input.length; i++)
                input[i] = random.nextBoolean();

            Board board = constructBoard(boardType, n);
            calculateBoard(board, input);
            saveBoard(board, "board.txt");
            board = loadBoard("board.txt");
            calculateBoard(board, input);

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

    private static Board constructBoard(int type, int n) {
        double startTime = System.nanoTime();
        Board board;
        if (type == 0) {
            board = new EncodedBoard(n);
            System.out.print("Encoded board constructed ");
        } else {
            board = new ObjectBoard(n);
            System.out.print("Object board constructed ");
        }
        System.out.println((System.nanoTime() - startTime) / 1000000 + " ms");
        return board;
    }

    private static void calculateBoard(Board board, boolean[] input) {
        double startTime = System.nanoTime();
        if (board.setInputs(input)) {
            boolean result = board.calculateInput();
            System.out.print("result = " + result + " ");
        }
        else
            System.out.println("Error: length of input array not a power of 2 ");
        System.out.println((System.nanoTime() - startTime) / 1000000 + " ms");
    }

    private static void saveBoard(Board board, String filename) {
        double startTime = System.nanoTime();
        provider.save(board,filename);
        System.out.print("board saved ");
        System.out.println((System.nanoTime() - startTime) / 1000000 + " ms");
    }

    private static Board loadBoard(String filename) {
        double startTime = System.nanoTime();
        Board board = provider.load(filename);
        System.out.print("board loaded ");
        System.out.println((System.nanoTime() - startTime) / 1000000 + " ms");
        return board;
    }
}

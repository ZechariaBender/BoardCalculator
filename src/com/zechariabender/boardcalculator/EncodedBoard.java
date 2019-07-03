package com.zechariabender.boardcalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Random;

public class EncodedBoard implements Board, Serializable {

    private boolean[] input;
    private int exponent;
    private Node root;
    private Random random;

    EncodedBoard(int exponent) {
        this.exponent = exponent;
        random = new Random();
        root = new Node((byte) random.nextInt(16));
        buildTree(root, exponent);
    }

    private class Node {
        private byte operator;
        private Node left, right;
        private Node(byte operator) {
            this.operator = operator;
        }
    }

    private void buildTree(Node node, int n) {
        if (n > 0) {
            node.left = new Node((byte) random.nextInt(16));
            node.right = new Node((byte) random.nextInt(16));
            buildTree(node.left, n - 1);
            buildTree(node.right, n - 1);
        }
    }

    @Override
    public int getExponent() {
        return exponent;
    }

    @Override
    public boolean setInputs(boolean[] input) {
        if (input.length == Math.pow(2, exponent)) {
            this.input = input;
            return true;
        }
        return false;
    }

    @Override
    public boolean calculateInput() {
        return calculate(root,(0));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *           #----------------------------#             *
     *           |  Boolean Operator Encoding |             *
     *           #----------------------------#             *
     *                                                      *
     *    left  1100    (truth table for the left           *
     *    right 1010            and right variables)        *
     *    ----------------------------------------------    *
     *      0 = 0000: FALSE     (always false)              *
     *      1 = 0001: NOR       (not or)                    *
     *      2 = 0010: X_RIGHT   (exclusive right)           *
     *      3 = 0011: N_LEFT    (not left                   *
     *      4 = 0100: X_LEFT    (exclusive left)            *
     *      5 = 0101: N_RIGHT   (not right)                 *
     *      6 = 0110: XOR       (exclusive or)              *
     *      7 = 0111: NAND      (not and)                   *
     *      8 = 1000: AND       (and)                       *
     *      9 = 1001: XNOR      (exclusive nor)             *
     *      A = 1010: RIGHT     (right value)               *
     *      B = 1011: NX_LEFT   (not exclusive right)       *
     *      C = 1100: LEFT      (left value)                *
     *      D = 1101: NX_RIGHT  (not exclusive right)       *
     *      E = 1110: OR        (or)                        *
     *      F = 1111: TRUE      (always true                *
     *                                                      *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // calculates result of boolean operation on values a and b
    // by checking the Xth digit of the operator code (from the right),
    // where X is the binary number comprised of a and b
    private boolean calculate(Node node, int index) {
        if (node == null || node.left == null || node.right == null)
            return input[index];

        int left =  calculate(node.left, (index * 2))
                ? 1 : 0; // cast boolean result to int
        int right = calculate(node.right,(index * 2 + 1))
                ? 1 : 0; // cast boolean result to int

        // shift operator bits right according to the binary representation
        // of left and right values
        return ((node.operator >> left * 2 + right) & 1) // return rightmost digit
                > 0; // cast int result back to boolean
    }

    private void writeObject(ObjectOutputStream aOutputStream)
            throws IOException {
        aOutputStream.writeInt(exponent);
        aOutputStream.writeChars(serialize(root));
    }
    private String serialize(Node node) {
        if (node == null) {
            return "#";
        }
        return Integer.toHexString(node.operator).charAt(0)
                + serialize(node.left)
                + serialize(node.right);
    }

    private static CharacterIterator iterator;
    private void readObject(ObjectInputStream aInputStream)
            throws ClassNotFoundException, IOException {
        exponent = aInputStream.readInt();
        StringBuilder b = new StringBuilder();
        while (true) {
            try {
                b.append((aInputStream.readChar()));
            } catch (IOException e) {
                break;
            }
        }
        iterator = new StringCharacterIterator(b.toString());
        root = deserialize();
    }
    private Node deserialize() {
        char current = iterator.current();
        iterator.next();
        if (current == '#')
            return null;
        int value = Character.digit(current,16);
        Node node = new Node((byte) value);
        node.left = deserialize();
        node.right = deserialize();
        return node;
    }
}
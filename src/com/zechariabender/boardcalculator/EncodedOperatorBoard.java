package com.zechariabender.boardcalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Random;

public class EncodedOperatorBoard implements Board, Serializable {

    private boolean[] input;
    private int exponent;
    private Node root;
    private Random random;

    EncodedOperatorBoard(int exponent) {
        this.exponent = exponent;
        if (exponent == 0)
            return;
        random = new Random();
        root = new Node((byte) random.nextInt(16));
        buildTree(root, exponent);
    }

    // in this implementation of board,
    // every node holds an operator CODE consisting
    // of a single HEX digit (4 bits), stored in
    // a byte - only half of it being used
    private class Node {
        private byte operator;
        private Node left, right;
        private Node(byte operator) {
            this.operator = operator;
        }
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
     *      B = 1011: XN_LEFT   (not exclusive right)       *
     *      C = 1100: LEFT      (left value)                *
     *      D = 1101: XN_RIGHT  (not exclusive right)       *
     *      E = 1110: OR        (or)                        *
     *      F = 1111: TRUE      (always true                *
     *                                                      *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private void buildTree(Node node, int n) {
        if (n > 1) {
            node.left = new Node((byte) random.nextInt(16));
            node.right = new Node((byte) random.nextInt(16));
            buildTree(node.left, n - 1);
            buildTree(node.right, n - 1);
        }
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
        if (exponent == 0)
            return input[0];
        return calculate(root,(0));
    }

    // calculates result of boolean operation on values a and b
    // by checking the Xth digit of the boolean operator code (from the right),
    // where X is the binary number comprised of a and b
    private boolean calculate(Node node, int index) {
        int left, right;
        // casting boolean results to int using '? 1 : 0'
        if (node.left != null) {
            left = calculate(node.left, (index * 2)) ? 1 : 0;
            right = calculate(node.right,(index * 2 + 1)) ? 1 : 0;
        } else {
            left = input[index * 2] ? 1 : 0;
            right = input[index * 2 + 1] ? 1 : 0;
        }
        // shift boolean operator bits right according to the binary representation
        // of left and right values
        return ((node.operator >> left * 2 + right) & 1) // return rightmost digit
                > 0; // cast int result back to boolean
    }

    private void writeObject(ObjectOutputStream aOutputStream)
            throws IOException {
        aOutputStream.writeInt(exponent);
        aOutputStream.writeChars(serialize());
    }
    @Override
    // create string representing entire node tree of the board
    public String serialize() {
        return serialize(root);
    }
    // recursively create string representing a node subtree in the board
    private String serialize(Node node) {
        if (node == null) {
            return "#";
        }
        return Integer.toHexString(node.operator).charAt(0)
                + serialize(node.left)
                + serialize(node.right);
    }

    // create board object from text file
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
    // recursively create node subtree from its string representation
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

    @Override
    public int getExponent() {
        return exponent;
    }

    @Override
    public int getRootOperator() {
        return root.operator;
    }
}
package com.zechariabender.boardcalculator;

import com.zechariabender.boardcalculator.booleanoperator.*;

import java.io.Serializable;
import java.util.Random;

public class ObjectOperatorBoard implements Board, Serializable {

    private boolean[] input;
    private int exponent;
    private Node root;
    private Random random;

    ObjectOperatorBoard(int exponent) {
        this.exponent = exponent;
        if (exponent == 0)
            return;
        random = new Random();
        root = new Node(operatorResolver());
        buildTree(root, exponent);
    }

    // in this implementation of board,
    // every node holds an operator OBJECT
    private class Node implements Serializable {
        private BooleanOperator operator;
        private Node left;
        private Node right;
        private Node(BooleanOperator operator) {
            this.operator = operator;
        }
    }

    private void buildTree(Node node, int n) {
        if (n > 1) {
            node.left = new Node(operatorResolver());
            node.right = new Node(operatorResolver());
            buildTree(node.left, n - 1);
            buildTree(node.right, n - 1);
        }
    }

    // this board type so far only has 6 operators
    private BooleanOperator operatorResolver() {
        switch (random.nextInt(6)) {
            case 1: return new TRUEOperator();
            case 2: return new ANDOperator();
            case 3: return new OROperator();
            case 4: return new NANDOperator();
            case 5: return new XOROperator();
            default: return new FALSEOperator(); // default covers case: 0
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

    private boolean calculate(Node node, int index) {
        if (node.left == null)
            return node.operator.calculate(
                    input[index * 2],
                    input[index * 2 + 1]);
        return node.operator.calculate(
                // add digit 0 to the binary number of current index
                calculate(node.left, index * 2),
                // add digit 1 to the binary number of current index
                calculate(node.right, index * 2 + 1)
        );
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
        return node.operator.getClass().getSimpleName()
                + serialize(node.left)
                + serialize(node.right);
    }

    @Override
    public int getExponent() {
        return exponent;
    }

    @Override
    // method used for testing
    public int getRootOperator() {
        switch (root.operator.getClass().getSimpleName()) {
            case "FALSEOperator": return 0;
            case "NANDOperator": return 7;
            case "ANDOperator": return 8;
            case "OROperator": return 14;
            case "TRUEOperator": return 15;
            case "XOROperator": return 6;
            default: return 0;
        }
    }
}
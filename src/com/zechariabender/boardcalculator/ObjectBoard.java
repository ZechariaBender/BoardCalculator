package com.zechariabender.boardcalculator;

import com.zechariabender.boardcalculator.operator.*;

import java.io.Serializable;
import java.util.Random;

public class ObjectBoard implements Board, Serializable {

    private boolean[] input;
    private int exponent;
    private Node root;
    private Random random;

    ObjectBoard(int exponent) {
        this.exponent = exponent;
        random = new Random();
        root = new Node(operatorResolver());
        buildTree(root, exponent);
    }

    private class Node implements Serializable {
        private BooleanOperator operator;
        private Node left;
        private Node right;
        private Node(BooleanOperator operator) {
            this.operator = operator;
        }
    }

    private void buildTree(Node node, int n) {
        if (n > 0) {
            node.left = new Node(operatorResolver());
            node.right = new Node(operatorResolver());
            buildTree(node.left, n - 1);
            buildTree(node.right, n - 1);
        }
    }

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

    private boolean calculate(Node node, int index) {

        if (node == null || node.left == null || node.right == null)
                return input[index];

        return node.operator.calculate(
                // add digit 0 to the binary number of current index
                calculate(node.left, index * 2),
                // add digit 1 to the binary number of current index
                calculate(node.right, index * 2 + 1)
        );
    }
}
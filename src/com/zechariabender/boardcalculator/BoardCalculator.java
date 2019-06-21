package com.zechariabender.boardcalculator;

public class BoardCalculator {

    private boolean[] input;
    private int exponent;

    // only accept input array of length which is a power of 2
    public boolean setInputs(boolean[] input, int exponent) {
        if (input.length == Math.pow(2, exponent)) {
            this.input = input;
            this.exponent = exponent;
            return true;
        }
        return false;
    }

    // every call to calculate() will generate a completely new
    // and temporary random board
    public boolean calculate() {
        return new Node(BooleanOperatorFactory
                .generateRandomOperator(), exponent).calculate(0);
    }

    private class Node {

        private BooleanOperator operator;
        private Node left;
        private Node right;

        private Node(BooleanOperator operator, int n) {
            this.operator = operator;
            if (n <= 0) { // we've descended n levels - make this node a leaf
                this.left = null;
                this.right = null;
            } else {
                this.left = new Node(BooleanOperatorFactory
                        .generateRandomOperator(), n - 1);
                this.right = new Node(BooleanOperatorFactory
                        .generateRandomOperator(), n - 1);
            }
        }

        private boolean calculate(int index) {
            if (left == null || right == null) { // this node is a leaf
                try {

//                System.out.println("calculating "
//                        + operator.getClass().getSimpleName()
//                        + " on input[" + (index) + "]");

                    return input[index];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // this node is not a leaf - recurse downwards through children
            try {

//                    System.out.println("calculating "
//                            + operator.getClass().getSimpleName()
//                            + ", index " + index);

                boolean result = operator.calculate(

                        // add digit 0 to the binary number of current index
                        left.calculate(index * 2),

                        // add digit 1 to the binary number of current index
                        right.calculate(index * 2 + 1)
                );

//                    System.out.println(operator.getClass().getSimpleName()
//                            + " returning " + result
//                            + ", index " + index);

                return result;

            } catch (Throwable t) {
                System.out.println("Error at node " + index);
                throw t;
            }
        }
    }
}
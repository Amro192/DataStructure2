package com.othman.file_dialogs;

import com.othman.data.Result;
import com.othman.structures.Stack;

/*
 * This class is used to evaluate infix and postfix expressions.
 */
public class Evaluate {
    /*
     *This method checks the precedence of the operator.
     */

    private static int value(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    /*
     *This method checks if the character is an operator.
     */

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == '%';
    }
    /*
     *This method checks if the character is an operand or dot.
     */

    private static boolean isOperand(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }
    /*
     *This method evaluates the expression.
     */

    private static void operation(String s, Stack operandStack, double popLeft, double popRight) {
        switch (s) {
            case "^" -> operandStack.push(String.valueOf(Math.pow(popLeft, popRight)));
            case "%" -> operandStack.push(String.valueOf(popLeft % popRight));
            case "*" -> operandStack.push(String.valueOf(popLeft * popRight));
            case "/" -> operandStack.push(String.valueOf(popLeft / popRight));
            case "+" -> operandStack.push(String.valueOf(popLeft + popRight));
            case "-" -> operandStack.push(String.valueOf(popLeft - popRight));
        }
    }
    /*
     *This method converts infix to postfix.
     */

    //complexity: O(n)
    private static String infixToPostfix(String infix) {
        Stack stack = new Stack();
        StringBuilder postfix = new StringBuilder();
        // Iterate over the infix string
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // Handle spaces:
            if (Character.isWhitespace(c)) {
                continue;
            }

            // Handle numbers:
            if (Character.isDigit(c)) {

                StringBuilder numStr = new StringBuilder(); // For multi-digits numbers

                // Keep appending while there are digits or dots in the number:
                while (i < infix.length() && (Character.isDigit(c) || c == '.')) {

                    numStr.append(c); // Append digit to the number string
                    i++; // Move to the next character
                    if (i < infix.length()) {
                        c = infix.charAt(i);
                    }
                }
                postfix.append(numStr);
                postfix.append(" ");
                i--; // Adjust for the extra increment in the loop
                continue; // Go to the next character in the infix string
            }

            // Handling operators and parentheses using string stack:
            if (c == '(') {
                stack.push(c + ""); // Push as a string
            } else if (c == ')') {

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" "); // Append with space
                }
                stack.pop(); // Remove the opening parenthesis

            } else { // Operator
                while (!stack.isEmpty() && !stack.peek().equals("(") && value(c) <= value(stack.peek().charAt(0))) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(c + "");
            }
        }

        // Append any remaining operators from the stack:
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }
        // Free the stack:
        stack.free();
        return postfix.toString();
    }

    /*
     *This method evaluates the infix expression.
     */

    // complexity: O(n)
    public static Result infixEvaluation(String infix) {
        Stack stack = new Stack();
        String postfix = infixToPostfix(infix); // Convert string to postfix
        // Iterate over the postfix string
        for (String s : postfix.split("\\s+")) {
            try {

                Double.parseDouble(s); // Try to parse the string as a number
                stack.push(s); // Push the number to the stack

            } catch (NumberFormatException e) {

                double popRight = Double.parseDouble(stack.pop()); // Pop the first operand as a right operand
                double popLeft = Double.parseDouble(stack.pop()); // Pop the left operand as a left operand
                operation(s, stack, popLeft, popRight); // Perform the operation and push the result to the stack
            }
        }
        // The final result is the only element in the stack:
        double result = Double.parseDouble(stack.pop());
        stack.free();
        // Return the result:
        return new Result(infix, postfix, result);
    }

    /*
     *This method converts infix to prefix.
     */

    //complexity: O(n)
    private static String postfixToPrefix(String string) {
        Stack stack = new Stack();
        String[] split = string.split("\\s+"); // Split the string by spaces

        for (String str : split) { // Iterate over the split string
            if (str.isBlank()) { // Skip empty strings
                continue;
            }

            char ch = str.charAt(0); // Get the first character of the string
            if (isOperand(ch)) { // If the first character is a digit or dot
                stack.push(str);
            } else if (isOperator(ch)) { // If the first character is an operator
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String expression = ch + " " + operand1 + " " + operand2;
                stack.push(expression);
            }
        }

        String pop = stack.pop();
        stack.free();

        // The final result is the only element in the stack:
        return pop;
    }

    /*
     *This method evaluates the postfix expression.
     */

    //complexity: O(n)
    public static Result postfixEvaluation(String string) {

        String prefix = postfixToPrefix(string); // Convert postfix to prefix
        Stack stack = new Stack(); // Create a new stack
        String[] split = prefix.split("\\s+"); // Split the string by spaces

        // Iterate over the split string
        for (int i = split.length - 1; i >= 0; i--) {
            String str = split[i]; // Get the current string
            char firstChar = str.charAt(0); // Get the first character of the string

            // If the first character is an operator
            if (isOperator(firstChar)) {
                double popRight = Double.parseDouble(stack.pop());
                double popLeft = Double.parseDouble(stack.pop());
                operation(str, stack, popRight, popLeft);
                // If the first character is a digit or dot or negative sign
            } else if (Character.isDigit(firstChar) || (firstChar == '-' && str.length() > 1)) {
                double operand = Double.parseDouble(str);
                stack.push(String.valueOf(operand));
            }
        }
        // The final result is the only element in the stack:
        double result = Double.parseDouble(stack.pop());
        stack.free();
        // Return the result:
        return new Result(string, prefix, result);
    }

}

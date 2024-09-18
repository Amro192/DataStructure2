package com.othman.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the infix and postfix expressions.
 * It is used to store the results of the conversion.
 */
public class Section {
    /**
     * The infix and postfix expressions are stored in lists.
     */
    private final List<Result> infix = new ArrayList<>();

    private final List<Result> postfix = new ArrayList<>();

    /**
     * Adding a result to the infix list.
     */
    public void addInfix(Result result) {
        infix.add(result);
    }

    /**
     * Adding a result to the postfix list.
     */
    public void addPostfix(Result result) {
        postfix.add(result);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("infix: \n");

        for (Result s : infix) {
            stringBuilder.append("\t").append(s).append("\n");
        }

        stringBuilder.append("Postfix: \n");
        for (Result s : postfix) {
            stringBuilder.append("\t").append(s).append("\n");
        }

        return stringBuilder.toString();
    }
}

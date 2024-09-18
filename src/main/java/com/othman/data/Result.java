package com.othman.data;

/**
 * This class is used to store the result of the conversion
 * It is a compact class that is used to store data
 */
public record Result(String input, String converted, double result) {
    @Override
    public String toString() {
        return input + " ==> " + converted + " ==> " + result;
    }
}

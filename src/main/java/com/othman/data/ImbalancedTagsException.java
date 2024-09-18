package com.othman.data;

/**
 * Thrown when the file is not balanced
 */
public class ImbalancedTagsException extends RuntimeException {
    /**
     * public constructor
     */
    public ImbalancedTagsException() {
        super("File is not balanced");
    }
}

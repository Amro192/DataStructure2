package com.othman.structures;

import com.othman.app.App;

/*
 *  Stack implementation
 */
public class Stack {

    /*
     *  Stack head
     */
    private final int stackHead;
    /*
     * Cursor instance singleton
     */

    private final Cursor cursor = App.getInstance().getCursor();
    /*
     *  Free flag
     */
    private boolean isFree = false;

    /*
     *  Constructor calling createStack method
     */
    public Stack() {
        this.stackHead = cursor.createStack();
    }

    /*
     *  Check if the stack has been freed
     */
    private void isFreed() {
        if (isFree) {
            throw new RuntimeException("Stack has been freed");
        }
    }

    /*
     *  Get the stack head
     */
    public String peek() {
        return (String) cursor.getFirst(stackHead);
    }

    /*
     *  Push an element to the stack
     */
    public void push(String elementToAdd) {
        isFreed();
        cursor.add(elementToAdd, stackHead);
    }

    /*
     *  Pop an element from the stack
     */
    public String pop() {
        isFreed();
        return (String) cursor.remove(stackHead);
    }

    /*
     *  Free the stack
     */
    public void free() {
        isFreed();
        cursor.free(stackHead);
        isFree = true;
    }

    /*
     *  Check if the stack is empty
     */
    public boolean isEmpty() {
        isFreed();
        return cursor.isEmpty(stackHead);
    }

    @Override
    public String toString() {
        return cursor.toString(stackHead);
    }
}

package com.othman.structures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 *  Cursor implementation
 */
public class Cursor {

    /*
     *  Array of cursor nodes
     */
    private Node[] cursorArray;

    /*
     *  Max size of the array
     */
    private int maxSize = 10;

    /*
     *  Constructor calling initialization method
     */
    public Cursor() {
        initialization();
    }

    /*
     *  Initialization method
     */
    private void initialization() {
        cursorArray = new Node[maxSize];
        // Initialize the array with null nodes
        for (int i = 0; i < maxSize; i++) {
            cursorArray[i] = new Node(null, i + 1);
        }
        // Set the last node to 0
        cursorArray[0].setElement("Free");
        cursorArray[maxSize - 1].setNext(0);
    }

    /*
     * Check if the stack is empty
     */
    public boolean isEmpty(int stackHead) {
        return cursorArray[stackHead].getNext() == 0;
    }

    /*
     * Check if the stack is full
     */
    public boolean isFull() {
        return cursorArray[0].getNext() == 0;
    }

    /*
     *  Allocate a new node
     */
    private int cursorAlloc() {
        int position = cursorArray[0].getNext();
        cursorArray[0].setNext(cursorArray[position].getNext());
        return position;
    }

    /*
     *  Create a new stack
     */
    public int createStack() {
        int list = cursorAlloc();
        if (list == 0) return -1;
        else {
            cursorArray[list] = new Node("-", 0);
            return list;
        }
    }

    /*
     *  Free a specific node
     */
    private void cursorFree(int position) {
        cursorArray[position].setElement(null);
        cursorArray[position].setNext(cursorArray[0].getNext());
        cursorArray[0].setNext(position);
    }

    /*
     *  Clear a specific stack
     */
    public void clear(int head) {
        int position = cursorArray[head].getNext();
        while (position != 0) {
            int next = cursorArray[position].getNext();
            cursorFree(position);
            position = next;
        }
    }

    /*
     *  Free a specific stack
     */
    public void free(int stackHead) {
        clear(stackHead);
        cursorFree(stackHead);
    }

    /*
     *  Remove the first element of a specific stack
     */

    //complexity: O(1)
    public Object remove(int stackHead) {
        if (isEmpty(stackHead)) {
            return null;
        }
        int position = cursorArray[stackHead].getNext();

        String removed = (String) cursorArray[position].getElement();
        cursorArray[stackHead].setNext(cursorArray[position].getNext());

        cursorFree(position);
        return removed;
    }

    /*
     *  Iterator implementation
     */

    //complexity: O(1)
    public Iterator iterator(int stackHead) {
        return new Iterator<>() {
            private int position = cursorArray[stackHead].getNext();

            @Override
            public boolean hasNext() {
                return position != 0;
            }

            @Override
            public Object next() {
                if (!hasNext()) throw new NoSuchElementException("Out of elements!");
                Object element = cursorArray[position].getElement();
                position = cursorArray[position].getNext();
                return element;
            }
        };
    }

    /*
     *  Add a new element to a specific stack
     */

    //complexity: O(n)
    public boolean add(Object object, int stackHead) {
        // If the stack is full, double the size of the array
        if (isFull()) {
            cursorArray = Arrays.copyOf(cursorArray, maxSize * 2);
            for (int i = maxSize; i < maxSize * 2; i++) {
                cursorArray[i] = new Node(null, i + 1);
            }
            cursorArray[0].setNext(cursorArray[maxSize].getNext());
            maxSize *= 2;
            cursorArray[maxSize - 1].setNext(0);
        }
        // Allocate a new node
        int position = cursorAlloc();
        if (position != 0) {
            cursorArray[position] = new Node(object, cursorArray[stackHead].getNext());
            cursorArray[stackHead].setNext(position);
            return true;
        } else {
            return false;
        }
    }

    /*
     *  Get the first element of a specific stack
     */

    //complexity: O(1)
    public Object getFirst(int stackHead) {
        if (isEmpty(stackHead)) {
            return null;
        }
        int next = cursorArray[stackHead].getNext();
        return cursorArray[next].getElement();
    }


    //complexity: O(n)
    public String toString(int head) {
        StringBuilder st = new StringBuilder();
        Iterator iterator = iterator(head);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            st.append(next).append("\n");
        }
        return st.toString();
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "maxSize=" + maxSize +
                '}';
    }
}

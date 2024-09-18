package com.othman.structures;

/*
 *  Node class for cursor implementation
 */
public class Node {
    /*
     *  Element of the node
     */
    private Object element;
    /*
     *  Next node
     */
    private int next;

    public Node(Object o, int i) {
        element = o;
        next = i;
    }

    public Node(Object o) {
        this(o, 0);
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                ", next=" + next +
                '}';
    }
}

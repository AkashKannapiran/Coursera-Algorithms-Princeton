/* *****************************************************************************
 *  Name: Akash Kannapiran
 *  Date: 19-March-2026
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;

    private Node<Item> first;

    private Node<Item> last;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        private DequeIterator(Node<Item> item) {
            current = item;
        }


        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to deque");
        }

        Node<Item> newFirst = new Node<>();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.prev = null;

        if (isEmpty()) {
            last = newFirst;
        }
        else {
            first.prev = newFirst;
        }

        first = newFirst;
        size++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to deque");
        }

        Node<Item> newLast = new Node<>();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;

        if (isEmpty()) {
            first = newLast;
        }
        else {
            last.next = newLast;
        }

        last = newLast;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove from empty deque");
        }

        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove from empty deque");
        }

        Item item = last.item;
        last = last.prev;
        size--;

        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return null;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        String text = "World";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = ", ";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "Hello";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "Meow, ";
        deque.addFirst(text);
        StdOut.println("addFirst() with: '" + text + "'");

        text = "^^";
        deque.addLast(text);
        StdOut.println("addLast() with: '" + text + "'");

        deque.removeFirst();
        StdOut.println("removeFirst()");

        deque.removeLast();
        StdOut.println("removeLast()");

        text = "!";
        deque.addLast(text);
        StdOut.println("addLast() with: '" + text + "'");

        StdOut.println("Iterating deque...");

        for (String item : deque) {
            StdOut.println("Iterate element: " + item);
        }
    }

}
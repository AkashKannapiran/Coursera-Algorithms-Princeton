/* *****************************************************************************
 *  Name: Akash Kannapiran
 *  Date:19-March-2026
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;

    private Item[] items;

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] itemsCopy;

        private int sizeCopy;

        private RandomizedQueueIterator() {
            sizeCopy = size;
            itemsCopy = (Item[]) new Object[sizeCopy];

            for (int i = 0; i < sizeCopy; i++) {
                itemsCopy[i] = items[i];
            }
        }

        public boolean hasNext() {
            return (sizeCopy > 0);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            int index = StdRandom.uniform(sizeCopy);
            Item item = itemsCopy[index];
            itemsCopy[index] = itemsCopy[--sizeCopy];
            itemsCopy[sizeCopy] = null;

            return item;
        }
    }


    // construct an empty randomized queue
    public RandomizedQueue() {
        int defaultItemsSize = 2;

        items = (Item[]) new Object[defaultItemsSize];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add empty element to queue");
        }

        if (size == items.length) {
            resizeItems(items.length * 2);
        }

        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = items[--size];
        items[size] = null;

        if (size > 0 && (size == items.length / 4)) {
            resizeItems(items.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int index = StdRandom.uniform(size);

        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resizeItems(int length) {
        Item[] newItems = (Item[]) new Object[length];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        String text = "A";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "B";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "C";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "D";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        text = "E";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        queue.dequeue();
        StdOut.println("dequeue()");

        queue.sample();
        StdOut.println("sample()");

        text = "F";
        queue.enqueue(text);
        StdOut.println("enqueue() with: '" + text + "'");

        StdOut.println("Iterating queue...");

        for (String item : queue) {
            StdOut.println("Iterate element: " + item);
        }
    }

}
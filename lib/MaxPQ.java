package lib;

import java.util.Arrays;

public class MaxPQ<Key extends Comparable<Key>> {

    private int size;
    Key[] heap;

    /**
     * init a new PQ with size 10
     */
    public MaxPQ() {
        this(10);
    }

    /**
     * init a new PQ with size max
     * 
     * @param max : size of PQ
     */
    public MaxPQ(int max) {
        if (max < 1)
            throw new Error("Too small number, must be max >= 1 ");
        heap = (Key[]) new Comparable[max];
        size = 0;
    }

    /**
     * init a new PQ from an array
     * 
     * @param a : the array
     */
    public MaxPQ(Key[] a) {
        int n = a.length;
        size = n;
        heap = (Key[]) new Comparable[n + 1];

        for (int i = 1; i < n + 1; i++) {
            heap[i] = a[i - 1];
        }
        for (int i = n / 2; i > 0; i--) {
            sink(i);
        }

    }

    /**
     * insert a new key to PQ
     * 
     * @param v : the new key
     */
    public void insert(Key v) {
        // if the array becomes small,
        if (size >= heap.length - 1) // // (a.length -1) because we start from index 1
            heap = Arrays.copyOf(heap, heap.length * 2);
        heap[++size] = v;
        swim(size); // reheapify
    }

    /**
     * reheapify from top to down
     * 
     * @param k : key to be placed
     */
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k / 2, k);
            k /= 2;
        }
    }

    private void exchange(int i, int k) {
        Key temp = heap[i];
        heap[i] = heap[k];
        heap[k] = temp;
    }

    private boolean less(int i, int k) {
        return heap[i].compareTo(heap[k]) < 0;
    }

    public Key max() {
        if (isEmpty())
            return null;
        return heap[1];
    }

    public Key delMax() {
        Key oldMax = max();
        exchange(1, size);
        heap[size--] = null;
        if (!isEmpty())
            sink(1);
        return oldMax;
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && less(j, j + 1))
                j++;
            if (!less(k, j))
                break;
            exchange(k, j);
            k = j;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }
    // contains key
    public boolean contains(Key key) {
        for (int i = 1; i <= size; i++) {
            if (heap[i].equals(key))
                return true;
        }
        return false;
    }
    int size() {
        return size;
    }

}

package com.example.continuesaggregatesample.util;

import java.util.Iterator;

public class PeekingIterator<E> implements Iterator<E> {
    private Iterator<E> it;
    private E current;

    public PeekingIterator(Iterator<E> it) {
        this.it = it;
        if (it.hasNext()) current = it.next();
    }

    public boolean hasNext() {
        return current != null;
    }

    public E next() {
        if (!hasNext()) throw new RuntimeException("No more elements");

        E result = current;
        current = it.hasNext() ? it.next() : null;
        return result;
    }

    public E peek() {
        if (!hasNext()) throw new RuntimeException("No more elements to peek");
        return current;
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove operation not supported");
    }
}


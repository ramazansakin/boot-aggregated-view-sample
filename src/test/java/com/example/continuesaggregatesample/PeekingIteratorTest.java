package com.example.continuesaggregatesample;


import com.example.continuesaggregatesample.util.PeekingIterator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PeekingIteratorTest {

    @Test
    public void testPeek() {
        // Test case 1
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it1 = list1.iterator();
        PeekingIterator<Integer> pi1 = new PeekingIterator<>(it1);
        assertEquals(Integer.valueOf(1), pi1.peek());
        assertEquals(true, pi1.hasNext());
        assertEquals(Integer.valueOf(1), pi1.next());

        // Test case 3
        ArrayList<Integer> list3 = new ArrayList<>();
        Iterator<Integer> it3 = list3.iterator();
        PeekingIterator<Integer> pi3 = new PeekingIterator<>(it3);
        try {
            pi3.peek();
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            // Expected exception
        }
    }
}
